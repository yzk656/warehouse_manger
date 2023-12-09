package com.pn.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.mapper.RoleMapper;
import com.pn.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
//指定缓存的名称
@CacheConfig(cacheNames = "com.pn.service.impl.RoleServiceImpl")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    //指定缓存的键
    @Cacheable(key = "'all:role'")
    public Result roleList() {
        //查询所有启用的角色
        List<Role> list = list(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleState, 1)
        );

        return Result.ok(list);
    }


    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户Id查询分配的所以角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findUserRole(Integer userId) {
        return roleMapper.findUserRoleByUserId(userId);
    }
}
