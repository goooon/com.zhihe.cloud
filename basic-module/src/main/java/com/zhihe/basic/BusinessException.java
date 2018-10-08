package com.zhihe.basic;

public class BusinessException extends Exception {
    public BusinessException(Errors e){

    }
    public BusinessException(int code, String label) {
    }
}
