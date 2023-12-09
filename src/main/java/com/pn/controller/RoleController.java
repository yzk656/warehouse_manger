package com.pn.controller;

import com.pn.entity.Result;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色
     * @return
     */
    @GetMapping("/role-list")
    public Result roleList(){
        return roleService.roleList();
    }
}
