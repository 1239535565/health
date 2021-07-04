package com.hbnu.utils;


/*
    响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    //成功
    SUCCESS(200,"成功"),

    //参数错误
    FAIL(400,"参数错误"),

    //未认证（签名错误）
    UNAUTHORIZED(401,"未认证，签名错误"),

    //接口不存在
    NOT_FOUND(404,"接口不存在"),

    //服务器内部错误
    INTERNAL_SERVER_ERROR(500,"服务器内部错误");


    private  Integer status;
    private  String  msg;

    ResultCode(Integer status,String msg) {
        this.status = status;
        this.msg=msg;
    }

    public String msg() {
        return this.msg;
    }

    public Integer status() {
        return this.status;
    }
}
