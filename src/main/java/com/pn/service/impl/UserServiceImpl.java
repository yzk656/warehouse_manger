package com.pn.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.entity.UserRole;
import com.pn.mapper.UserMapper;
import com.pn.mapper.UserRoleMapper;
import com.pn.page.Page;
import com.pn.service.RoleService;
import com.pn.service.UserRoleService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author: 杨振坤
 * @date: 2023/7/15 14:13
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //根据账号查询用户
    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param user
     * @return
     */
    @Override
    public Page queryUserByPage(Page page, User user) {
        //查询用户行数
        Integer rowCount = userMapper.findUserRowCount(user);

        //分页查询用户信息
        List<User> userByPage = userMapper.findUserByPage(page, user);

        //组装所有分页信息
        page.setTotalNum(rowCount);
        page.setResultList(userByPage);

        //进行响应
        return page;
    }

    /**
     * 添加用户业务
     *
     * @param user
     * @return
     */
    @Override
    public Result saveUser(User user) {
        //判断账号是否已经存在
        User userByCode = userMapper.findUserByCode(user.getUserCode());
        if (userByCode != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "账号已经存在");
        }

        //对密码进行加密
        String password = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);

        //执行添加
        int i = userMapper.insertUser(user);
        if (i > 0) {
            return Result.ok("用户添加成功");
        }
        return Result.ok("用户添加失败");
    }

    /**
     * 启用或者禁用用户
     *
     * @param user
     * @return
     */
    @Override
    public Result updateUserState(User user) {
        //参数校验
        if (StringUtils.isEmpty(user)) {
            return Result.err(Result.PARAM_INVALID, "参数校验失败");
        }

        //更新数据
        int i = userMapper.updateUserState(user.getUserId(), user.getUserState());
        if (i > 0) {
            return Result.ok("更新成功");
        } else {
            return Result.err(Result.CODE_ERR_BUSINESS, "更新失败");
        }
    }

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 分配角色
     *
     * @return
     */
    @Override
    public Result assignRole(AssignRoleDto dto) {
        //获取角色信息
        ArrayList<UserRole> userRoles = new ArrayList<>();

        //删除之前的所有关系
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId,dto.getUserId())
        );

        //查询角色信息进行赋值
        for (String s : dto.getRoleCheckList()) {
            UserRole userRole = new UserRole();
            //获取roleId
            Role role = roleService.getOne(Wrappers.<Role>lambdaQuery()
                    .eq(!StringUtils.isEmpty(s), Role::getRoleName, s)
            );
            //如果是一条新数据
            if (role != null) {
                userRole.setRoleId(role.getRoleId());
                userRole.setUserId(dto.getUserId());
                userRoles.add(userRole);
            }
        }

        //插入userRole信息
        userRoleService.saveBatch(userRoles);

        return Result.ok("更新成功");
    }

}
