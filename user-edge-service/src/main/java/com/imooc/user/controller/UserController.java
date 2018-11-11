package com.imooc.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.user.dto.UserDTO;
import com.imooc.user.redis.RedisClient;
import com.imooc.user.response.LoginResponse;
import com.imooc.user.response.Response;
import com.imooc.user.thriftClient.ServiceProvider;
import com.imooc.user.util.CodeUtil;
import com.imooc.user.util.CommonUtil;
import com.imooc.user.util.Constant;
import om.imooc.thrift.user.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private CodeUtil codeUtil;

    /**
     * 用户登录服务
     * 1.验证用户名密码
     * 2.生成token
     * 3.缓存用户与 token的关系
    * @param username
     * @param password
     */
    @RequestMapping( value = "/login" , method = RequestMethod.POST)
    public Response login(@RequestParam("username")String username, @RequestParam("password")String password){
        //1.验证用户名密码
        UserInfo userInfo;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if(userInfo == null){
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if(! StringUtils.equals(userInfo.getPassword(), codeUtil.md5(password))){
            return Response.USERNAME_PASSWORD_INVALID;
        }

        //2.生成token
        String token = genToken();

        //3.缓存用户
        redisClient.set(token, toDTO(userInfo),3600);

        return new LoginResponse(token);
    }

    /**
     * 用户注册服务
     * 除了用户信息外还有验证码verifyCode
     * @param jsonObject
     * @return
     */
    public Response register(JSONObject jsonObject){
        if(StringUtils.isBlank(jsonObject.getString("phone"))
         || StringUtils.isBlank(jsonObject.getString("email"))){
            return Response.MISS_USER_INFO;
        }

        String verifyCode = jsonObject.getString("verifyCode");
        //一般来说时用手机进行验证的，只是这个服务要钱，所以现在就只用邮箱验证
        String redisVerifyCode = redisClient.get(jsonObject.getString("email"));
        if( ! StringUtils.equals(redisVerifyCode , verifyCode)){
            return Response.USER_VERIFY_CODE_ERROR;
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(jsonObject,userInfo);
        String password = userInfo.getPassword();
        userInfo.setPassword(codeUtil.md5(password));
        try {
            serviceProvider.getUserService().regiserUser( userInfo );
        } catch (TException e) {
            e.printStackTrace();
            return new Response(e);
        }
        return Response.SUCCESS;
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo,userDTO);
        return userDTO;
    }



    private String genToken(){
        return CommonUtil.randomCode(Constant.commondChar,32);
    }

}
