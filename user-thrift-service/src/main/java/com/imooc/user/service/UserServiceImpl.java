package com.imooc.user.service;

import com.imooc.user.mapper.UserMapper;
import om.imooc.thrift.user.UserInfo;
import om.imooc.thrift.user.UserService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        String userid = String.valueOf(id);
        return userMapper.getUserById(userid);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        System.out.println("getUserByName : " + username);
        UserInfo userInfo =  userMapper.getUserByName(username);
        System.out.println(userInfo);
        return userInfo;
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}
