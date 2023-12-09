package com.pn.service;

import com.pn.entity.Auth;

import java.util.List;

/**
 * @Author: 杨振坤
 * @date: 2023/8/8 23:04
 */
public interface AuthService {

    //查询用户菜单树的业务方法
    public List<Auth> findAuthTreeByUID(Integer UID);
}
