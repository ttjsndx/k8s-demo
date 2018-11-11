package com.imooc.user.response;

public class LoginResponse extends Response {
    private String token;

    public String getToken() {
        return token;
    }

    /*
      作为需要返回给前端的实体，get set方法时必要的
      否则spring 无法自动将字段转换成json
   */
    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse(String token){
        this.token = token;
    }
}
