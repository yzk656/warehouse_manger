package com.pn.filter;


import com.alibaba.fastjson.JSON;
import com.pn.entity.Result;
import com.pn.utils.WarehouseConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 杨振坤
 * @date: 2023/7/15 19:10
 */
public class LoginCheckFilter implements Filter {

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //过滤器拦截到请求执行的方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //白名单放行
        //创建白名单集合
        List<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");

        //过滤器拦截到的url接口
        String url = request.getServletPath();

        //白名单请求
        if (urlList.contains(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 其他请求判断是否携带token
         */
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if (StringUtils.hasText(token) && redisTemplate.hasKey(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //说明未登录或token过期，请求不放行
        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "您尚未登录");
        String jsonString = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
