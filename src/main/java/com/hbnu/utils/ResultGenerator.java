package com.hbnu.utils;


/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "OK";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 201;

    //成功，携带默认消息，不带数据
    public static Result genSuccessResult() {
        Result result = new Result();
        result.setMsg(DEFAULT_SUCCESS_MESSAGE);
        result.setStatus(RESULT_CODE_SUCCESS);
        return result;
    }

    //成功，携带自定义消息，不带数据
    public static Result genSuccessResult(String message) {
        Result result = new Result();
        result.setMsg(message);
        result.setStatus(RESULT_CODE_SUCCESS);
        return result;
    }

    //成功，携带默认消息，带数据
    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setMsg(DEFAULT_SUCCESS_MESSAGE);
        result.setStatus(RESULT_CODE_SUCCESS);
        return result;
    }

    //成功，携带自定义消息和数据
    public static Result genSuccessResult(String message,Object data) {
        Result result = new Result();
        result.setData(data);
        result.setMsg(message);
        result.setStatus(RESULT_CODE_SUCCESS);
        return result;
    }

    //请求失败，携带自定义消息
    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setMsg(message);
        result.setStatus(RESULT_CODE_SERVER_ERROR);
        return result;
    }

    //请求错误，携带状态码及消息
    public static Result genErrorResult(int code, String message) {
        Result result = new Result();
        result.setMsg(message);
        result.setStatus(code);
        return result;
    }
}
