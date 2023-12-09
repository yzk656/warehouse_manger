package com.pn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.entity.Result;
import com.pn.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 27939
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-12-08 12:31:00
* @Entity com.pn.entity.Role
*/

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户Id查询分配的所以角色
     * @param userId
     * @return
     */
    List<Role> findUserRoleByUserId(@Param("userId") Integer userId);
}




