package com.zhihe.basic;

public enum Errors {
    SYSTEM_NOT_LOGIN(1, "还未登录"),
    ACCESS_NOT_ALLOWED(2,"无权访问"),
    PARAMETER_ERROR(3, "参数错误");

    public int code;
    public String label;

    Errors(int code, String label) {
        this.code = code;
        this.label = label;
    }
}
