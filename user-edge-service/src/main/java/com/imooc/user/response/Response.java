package com.imooc.user.response;


import java.io.Serializable;

/**
 * 返回的类型
 */
public class Response implements Serializable {
    public static Response SUCCESS =
            new Response();

    public static Response USERNAME_PASSWORD_INVALID =
            new Response("1001","username or password invalid !");
    public static Response MISS_USER_INFO =
            new Response("1002","userInfo incomplete !");
    public static Response VERIFY_CODE_MISS_INFO =
            new Response("1003","phone or email is required in verify code !");
    public static Response VERIFY_CODE_ERROR =
            new Response("1004","send verify code failed !");
    public static Response USER_VERIFY_CODE_ERROR =
            new Response("1005","the verify is wrong !");
    private String code;
    private String message;

    /*
        作为需要返回给前端的实体，get set方法时必要的
        否则spring 无法自动将字段转换成json
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //针对所有的正常请求
    public Response(){
        this.code = "0";
        this.message = "success";
    }

    //针对所有的抛出异常错误
    public Response(Exception e){
        this.code = "9999";
        this.message = e.getMessage();
    }

    public Response(String code , String message){
        this.code = code;
        this.message = message;
    }
}
