package com.imooc.user.mapper;

import om.imooc.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT USERID,USERNAME,PASSWORD,REALNAME AS realName, " +
            "PHONE, EMAIL FROM PE_USER WHERE USERID=#{userid}")
    UserInfo getUserById(@Param("id") String userid);

    @Select("SELECT USERID,USERNAME,PASSWORD,REALNAME AS realName, " +
            "PHONE, EMAIL FROM PE_USER WHERE USERNAME=#{username}")
    UserInfo getUserByName(@Param("username") String username);

    @Insert("INSERT INTO PE_USER(USERID,USERNAME,PASSWORD,REALNAME,PHONE,EMAILL)" +
            "VALUES (#{u.userid},#{u.username},#{u.password},#{u.realName},#{u.phone},#{u.emaill})")
    void registerUser(@Param("u") UserInfo userInfo);
}
