package com.wckj.chasstage.common.util;

import com.wckj.framework.api.ApiReturnResult;

import java.util.HashMap;
import java.util.Map;

public final class ResultUtil<T> {
    private ResultUtil(){}

    public static <T> ApiReturnResult<T> ReturnSuccess(){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode(ApiReturnResult.SUCCESS_CODE);
        result.setMessage("请求成功!");
        result.setIsSuccess(true);
        return result;
    }

    public static <T> ApiReturnResult<T> ReturnSuccess(String message){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode(ApiReturnResult.SUCCESS_CODE);
        result.setMessage(message);
        result.setIsSuccess(true);
        return result;
    }

    public static <T> ApiReturnResult<T> ReturnError(String message){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode(ApiReturnResult.ERROR_CODE);
        result.setMessage(message);
        result.setIsSuccess(false);
        return result;
    }

    public static <T> ApiReturnResult<T> ReturnSuccess(String message,T data){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode(ApiReturnResult.SUCCESS_CODE);
        result.setMessage(message);
        result.setIsSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> ApiReturnResult<T> ReturnSuccess(T data){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode(ApiReturnResult.SUCCESS_CODE);
        result.setMessage("请求成功!");
        result.setIsSuccess(true);
        result.setData(data);
        return result;
    }

    public static Map<String,Object> WebResult(ApiReturnResult<?> api){
        Map<String,Object> r = new HashMap<>();
        r.put("code",api.getCode());
        r.put("data",api.getData());
        r.put("message",api.getMessage());
        r.put("isSuccess",api.getIsSuccess());
        return r;
    }

    public static <T> ApiReturnResult<T> ReturnWarning(String message){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode("400");
        result.setMessage(message);
        result.setIsSuccess(true);
        return result;
    }

    public static <T> ApiReturnResult<T> ReturnWarning(String message, T data){
        ApiReturnResult<T> result = new ApiReturnResult<>();
        result.setCode("400");
        result.setMessage(message);
        result.setIsSuccess(true);
        result.setData(data);
        return result;
    }
}
