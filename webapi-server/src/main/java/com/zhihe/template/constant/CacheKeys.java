package com.zhihe.template.constant;

/**
 * Redis Cache中存储的数据所用的Key的前缀.
 *
 * @author lizheng
 */
public class CacheKeys {

    /**
     * 默认的访问Token的HTTP请求头的名字
     */
    public static final String ACCESS_DEFAULT_TOKEN_HEADER_NAME = "Zhihe-Default-Access-Token";

    /**
     * <pre>
     * JIULING_USER_KEY 缓存用户的key
     * 用法是: String key = ZHIHE_USER_ + 123;
     * </pre>
     */
    public static final String ZHIHE_USER_KEY = "ZHIHE_USER_";

    /**
     * JIULING_MOBILE_USER_KEY 缓存用户的key
     */
    public static final String ZHIHE_MOBILE_USER_KEY = "ZHIHE_MOBILE_USER_";

    /**
     * JIULING_USER用户过期时间(单位:秒，7天)
     */
    public static final int ZHIHE_USER_EXP_KEY = 60 * 60 * 24 * 7;


    /**
     * JIULING_USER用户注册验证码key
     */
    public static final String ZHIHE_USER_REGISTER_CODE_KEY = "ZHIHE_USER_REGISTER_CODE_KEY_";

    /**
     *  用户注册验证码过期时间(单位:秒，5分)
     */
    public static final int ZHIHE_USER_REGISTER_CODE_TIME_KEY = 60 * 5;
    /**
     *  用户忘记密码key
     */
    public static final String ZHIHE_USRE_FORGET_PASSWORD_CODE_KEY = "ZHIHE_USRE_FORGET_PASSWORD_CODE_KEY_";

}

