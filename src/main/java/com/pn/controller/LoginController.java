package com.pn.controller;

import com.google.code.kaptcha.Producer;
import com.pn.entity.*;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 杨振坤
 * @date: 2023/7/13 13:54
 */

@RestController
public class LoginController {

    //注入DefaultKaptcha的bean对象
    @Resource(name = "captchaProducer")
    private Producer producer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthService authService;

    /**
     * 生成验证码图片接口
     *
     * @param response
     */
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {

        ServletOutputStream outputStream = null;

        try {
            //生成验证码的文件
            String text = producer.createText();
            //生成验证码图片
            BufferedImage image = producer.createImage(text);
            //将验证码存储在redis中
            redisTemplate.opsForValue().set(text, "", 60 * 30, TimeUnit.SECONDS);

            //设置响应正文image/jpeg
            response.setContentType("image/ipeg");
            //将图片发送到前端
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            //刷新
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭字节输出流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping("/login")
    //接收并封装前端传递过来的JSON数据
    public Result login(@RequestBody LoginUser loginUser) {

        //拿到验证码
        String verificationCode = loginUser.getVerificationCode();
        if (!redisTemplate.hasKey(verificationCode)) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码错误");
        }

        //根据账号查询用户
        User user = userService.queryUserByCode(loginUser.getUserCode());

        //账号存在
        if (user != null) {
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {//用户已经审核
                //拿到用户密码
                String userPwd = loginUser.getUserPwd();
                //进行md5加密
                userPwd = DigestUtil.hmacSign(userPwd);

                if (userPwd.equals(user.getUserPwd())) {//密码合法
                    //生成jwt token并存储到Redis中
                    CurrentUser currentUser = new CurrentUser(user.getUserId(), user.getUserCode(), user.getUserName());
                    String token = tokenUtils.loginSign(currentUser, userPwd);

                    //向客户端发送jwt token
                    return Result.ok("登录成功", token);
                } else {
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码错误");
                }
            } else {//未审核
                return Result.err(Result.CODE_ERR_BUSINESS, "用户未审核");
            }
        } else {//账号不存在
            return Result.err(Result.CODE_ERR_BUSINESS, "账号不存在");
        }
    }


    /**
     * 获取请求头的token信息
     *
     * @param token
     * @return
     */
    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //解析token
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //将得到的用户信息进行返回
        return Result.ok(currentUser);
    }

    /**
     * 获取用户菜单树
     *
     * @param token
     * @return
     */
    @RequestMapping("/user/auth-list")
    public Result getUserAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //解析token
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        //获取菜单树
        List<Auth> authTreeList = authService.findAuthTreeByUID(userId);

        //响应
        return Result.ok(authTreeList);
    }

    /**
     * 退出登录
     * 技术从redis中删除这个键
     *
     * @return
     */
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //从redis中删除token这个键
        redisTemplate.delete(token);

        //响应
        return null;
    }
}
