package com.pn.exception;

/**
 * @Author: 杨振坤
 * @date: 2023/7/15 11:11
 */

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException{

    //只是创建异常对象
    public BusinessException() {
        super();
    }

    //创建异常对象同时指定异常信息
    public BusinessException(String message) {
        super(message);
    }
}
