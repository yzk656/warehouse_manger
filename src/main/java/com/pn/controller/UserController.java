package com.pn.controller;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;
import com.pn.service.RoleService;
import com.pn.service.UserRoleService;
import com.pn.service.UserService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 杨振坤
 * @date: 2023/8/9 11:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //注入userService
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserRoleService userRoleService;

    @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime localDateTime;
    /**
     * 分页查询用户数据
     *
     * @param page
     * @param user
     * @return
     */
    @RequestMapping("/user-list")
    public Result userList(Page page, User user) {
        //执行业务
        page = userService.queryUserByPage(page, user);

        //响应数据
        return Result.ok(page);
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前用户ID
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userByID = currentUser.getUserId();
        user.setCreateBy(userByID);

        //执行业务
        Result result = userService.saveUser(user);
        return result;
    }

    @RequestMapping("/updateState")
    public Result updateUserState(@RequestBody User user,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前用户ID
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        user.setUpdateBy(userId);

        //执行业务
        return userService.updateUserState(user);
    }

    @Autowired
    private RoleService roleService;

    @GetMapping("/user-role-list/{userId}")
    public Result userRole(@PathVariable Integer userId){
        return Result.ok(roleService.findUserRole(userId));
    }

    @PutMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto dto){
        return userService.assignRole(dto);
    }

}
