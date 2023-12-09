package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;

/**
 * @Author: 杨振坤
 * @date: 2023/7/15 14:11
 */
public interface UserService {
    //根据账号查询用户
    public User queryUserByCode(String userCode);

    //分页查询用户业务方法
    public Page queryUserByPage(Page page,User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public Result saveUser(User user);

    /**
     * 启用或者禁用用户
     * @param user
     * @return
     */
    public Result updateUserState(User user);

    /**
     * 分配角色
     * @return
     */
    Result assignRole(AssignRoleDto dto);
}
