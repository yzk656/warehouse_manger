package com.pn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 27939
* @description 针对表【auth_info(权限表)】的数据库操作Mapper
* @createDate 2023-08-08 21:52:57
* @Entity com.pn.entity.Auth
*/
@Mapper
public interface AuthMapper {
    //查询用户菜单树
    public List<Auth> findAuthByUID(Integer UID);
}




