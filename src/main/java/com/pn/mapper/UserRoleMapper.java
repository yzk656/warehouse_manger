package com.pn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 27939
* @description 针对表【user_role(用户角色表)】的数据库操作Mapper
* @createDate 2023-12-08 17:08:18
* @Entity com.pn.entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




