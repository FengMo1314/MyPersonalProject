package com.example.springbootweb.dao;

import com.example.springbootweb.pojo.Avatar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface AvatarDao {
    Avatar getById(@Param("avatar")Avatar avatar);
    Avatar gettByUId(@Param("avatar")Avatar avatar);
    Avatar gettByUName(@Param("avatar")Avatar avatar);
    Avatar gettByUrl(@Param("avatar")Avatar avatar);
    Integer addAvatar(@Param("avatar") Avatar avatar);
    Integer deleteAvatarById(@Param("avatar") Avatar avatar);
    Integer deleteAvatarByUId(@Param("avatar") Avatar avatar);
    Integer deleteAvatarByUname(@Param("avatar") Avatar avatar);
    Integer deleteAvatarByUrl(@Param("avatar") Avatar avatar);
}
