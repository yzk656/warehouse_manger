package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.entity.Result;
import com.pn.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface RoleService extends IService<Role> {

    /**
     * 查询所有角色
     * @return
     */
    Result roleList();

    /**
     * 根据用户Id查询分配的所以角色
     * @param userId
     * @return
     */
    List<Role> findUserRole(Integer userId);

}
