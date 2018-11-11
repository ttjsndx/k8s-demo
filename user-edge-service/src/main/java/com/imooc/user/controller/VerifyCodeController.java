package com.imooc.user.controller;

import com.imooc.user.redis.RedisClient;
import com.imooc.user.response.Response;
import com.imooc.user.thriftClient.ServiceProvider;
import com.imooc.user.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码服务类@2wl 20181108
 */
@RestController
public class VerifyCodeController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    /**
     * 发送验证码
     * 缺少手机或者邮箱不重复验证
     * @return
     */
    @RequestMapping(value = "/sendVerifyCode" , method = RequestMethod.POST)
    public Response sendVerifyCode(@RequestBody String email , @RequestBody String phone){
        String message = "Verify code is ";
        String code = CommonUtil.randomCode("0123456789",6);
        try {
            Boolean bool;
            if(StringUtils.isNotBlank(phone)){
                bool = serviceProvider.getMessageService().sendMobileMessage(phone , message + code);
            }else if(StringUtils.isNotBlank(email)){
                bool = serviceProvider.getMessageService().sendEmailMessage(email , message + code);
            }else {
                return Response.VERIFY_CODE_MISS_INFO;
            }
            if(bool){
                redisClient.set( StringUtils.isNotBlank(phone) ? phone : email , code);
                return Response.SUCCESS;
            }else{
                return Response.VERIFY_CODE_ERROR;
            }
        } catch (TException e) {
            return new Response(e);
        }
    }

}
