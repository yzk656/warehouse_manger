package com.pn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 杨振坤
 * @date: 2023/7/15 11:43
 */

@Mapper
public interface UserMapper {
    //根据账号查询用户信息
    public User findUserByCode(String userCode);

    /**
     * 查询用户总行数
     * @param user
     * @return
     */
    public Integer findUserRowCount(User user);

    /**
     * 查询用户分页数据
     * @param user
     * @return
     */
    public List<User> findUserByPage(@Param("page") Page page, @Param("user") User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int insertUser(User user);

    /**
     * 修改用户状态
     * @return
     */
    public int updateUserState(@Param("userId") Integer userId,@Param("userState") String userState);
}
