package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Auth;
import com.pn.entity.Result;
import com.pn.mapper.AuthMapper;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 杨振坤
 * @date: 2023/8/8 23:04
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询用户菜单树
     * 同时向Redis中缓存一份
     *
     * @param UID
     * @return
     */
    @Override
    public List<Auth> findAuthTreeByUID(Integer UID) {
        //从Redis中获取菜单树
        String authTree = redisTemplate.opsForValue().get("authTree:" + UID);
        //判断是否有菜单树
        if (StringUtils.hasText(authTree)) {
            //将JSON转换成List<Auth>
            List<Auth> authList = JSON.parseArray(authTree, Auth.class);
            return authList;
        }

        //如果缓存数据中没有，进行查询数据库
        List<Auth> allauthList = authMapper.findAuthByUID(UID);
        //将所有菜单List<Auth>转换成菜单树
        List<Auth> authTreeList = AllAuthToAuthTree(allauthList, 0);
        //将其存储在Redis中一份
        redisTemplate.opsForValue().set("authTree:" + UID,JSON.toJSONString(authTreeList));

        //响应
        return authTreeList;
    }

    /**
     * 将所有的菜单通过递归算法转换成菜单树
     *
     * @param authList
     * @param PID
     * @return
     */
    private List<Auth> AllAuthToAuthTree(List<Auth> authList, Integer PID) {
        List<Auth> firstAuthTree = new ArrayList<>();

        //查询相对意义上的一次菜单
        for (Auth auth : authList) {
            if (auth.getParentId().equals(PID)) {
                firstAuthTree.add(auth);
            }
        }

        //查询二级菜单,将其赋值给一次菜单的属性中
        for(Auth auth:firstAuthTree){
            List<Auth> secondAuthList = AllAuthToAuthTree(authList, auth.getAuthId());
            auth.setChildAuth(secondAuthList);
        }

        return firstAuthTree;
    }

}
