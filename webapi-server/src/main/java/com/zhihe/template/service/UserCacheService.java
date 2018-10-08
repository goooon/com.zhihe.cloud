package com.zhihe.template.service;

import com.zhihe.basic.BusinessException;
import com.zhihe.template.constant.CacheKeys;
import com.zhihe.template.domain.dataJpa.User;
import com.zhihe.basic.Errors;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserCacheService {

    @Resource
    private RedisCachedService redisCachedService;


    /**
     * 将验证码写入到缓存中，有效时间为5分钟
     *
     * @param code
     */
    public void cacheRegisterCode(String mobile, String code) {
        String key = CacheKeys.ZHIHE_USER_REGISTER_CODE_KEY + mobile;
        redisCachedService.set(key, CacheKeys.ZHIHE_USER_REGISTER_CODE_TIME_KEY, code);
    }

    /**
     * 获取缓存中的验证码
     *
     * @return
     */
    public String getCacheRegisterCode(String mobile) {
        String key = CacheKeys.ZHIHE_USER_REGISTER_CODE_KEY + mobile;
        Object object = redisCachedService.get(key);
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    /**
     * 删除缓存的手机号验证码
     * @param mobile
     */
    public void deleteCacheRegisterCode(String mobile){
        String key = CacheKeys.ZHIHE_USER_REGISTER_CODE_KEY + mobile;
        redisCachedService.delete(key);
    }
    /**
     * 将验证码写入到缓存中，有效时间为5分钟
     * 用户找回密码验证码写入缓存
     *
     * @param code
     */
    public void cacheforgetPasswordCode(String mobile, String code) {
        String key = CacheKeys.ZHIHE_USER_REGISTER_CODE_KEY + mobile;
        redisCachedService.set(key, CacheKeys.ZHIHE_USER_REGISTER_CODE_TIME_KEY, code);
    }
    /**
     * 获取找回密码缓存中的验证码
     *
     * @return
     */
    public String getCacheforgetPasswordCode(String mobile) {
        String key = CacheKeys.ZHIHE_USER_REGISTER_CODE_KEY  + mobile;
        Object object = redisCachedService.get(key);
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    /**
     * <pre>
     * 设置用户信息到缓存中
     *
     * <pre>
     *
     * @param request
     * @param user
     */
    public void cacheUser(HttpServletRequest request, User user) {
        String key = getUserCacheKey(request);
//		  String key = CacheKeys.ZHIHE_USER_KEY + generateAccessToken(request);
        redisCachedService.set(key, CacheKeys.ZHIHE_USER_EXP_KEY, user);
    }

    /**
     * <pre>
     * 设置用户信息到缓存中
     *
     * <pre>
     *
     * @param token
     * @param user
     */
    public void cacheUser(String  token, User user) {
//		String key = getUserCacheKey(request);
        String key = CacheKeys.ZHIHE_USER_KEY + token;
        redisCachedService.set(key, CacheKeys.ZHIHE_USER_EXP_KEY, user);
    }

    /**
     * <pre>
     * 设置用户信息到缓存中
     *
     * <pre>
     *
     * @param request
     * @param user
     */
    public void cacheMobileUser(HttpServletRequest request, User user) {
        String key = getUserCacheKey(request);
        redisCachedService.set(key, CacheKeys.ZHIHE_USER_EXP_KEY, user);
    }



    /**
     * 获取用户缓存key
     *
     * @param request
     * @return
     */
    private String getUserCacheKey(HttpServletRequest request) {
        String key = CacheKeys.ZHIHE_USER_KEY + getCacheKey(request);
//		  String key = CacheKeys.ZHIHE_USER_KEY + generateAccessToken(request);
        return key;
    }


    /**
     * 获取用户缓存key
     *
     * @param request
     * @return
     */
    private String getMobileUserCacheKey(HttpServletRequest request) {
        String key = CacheKeys.ZHIHE_MOBILE_USER_KEY + getCacheKey(request);
        return key;
    }


    /**
     * <pre>
     * 获取缓存key
     * 同时使用，使用token保存登录信息，优先使用token，如果获取失败则取session
     * </pre>
     *
     * @param request
     */
    private String getCacheKey(HttpServletRequest request) {
        String sessionId = request.getHeader(CacheKeys.ACCESS_DEFAULT_TOKEN_HEADER_NAME);
        if (StringUtils.isBlank(sessionId)) {
            Object sessionIdAttribute = request.getAttribute(CacheKeys.ACCESS_DEFAULT_TOKEN_HEADER_NAME);
            if (StringUtils.isNotBlank(sessionIdAttribute.toString())) {
                sessionId = sessionIdAttribute.toString();
            }
        }
        if (StringUtils.isBlank(sessionId)) {
            sessionId = request.getParameter("token");
        }
        if (StringUtils.isBlank(sessionId)) {
            sessionId = request.getSession().getId();
        }
        return sessionId;
    }
    /**
     * <pre>
     * 获取缓存用户，不为空，重新设置缓存中用户的过期时间
     * </pre>
     *
     * @param request
     * @return
     */
    public User getCacheUser(HttpServletRequest request) {
        String key = getUserCacheKey(request);
        Object obj = redisCachedService.get(key);
        if (obj == null) {
            throwException(Errors.SYSTEM_NOT_LOGIN);
        }
        User user = (User) obj;
	  /*  if (obj != null) {
	    	redisCachedService.set(key, CacheKeys. _USER_EXP_KEY, obj);
	    }*/
        return user;
    }
    /**
     * 删除缓存中得用户信息
     *
     * @param request
     */
    public void deleteCacheUser(HttpServletRequest request) {
        String key = getUserCacheKey(request);
        redisCachedService.delete(key);
    }


    /**
     * 生成登录权限token
     * 取sessionId做一次MD5加密
     * @param request
     * @return
     */
    public String generateAccessToken(HttpServletRequest request) {
        return request.getSession().getId();
//	    return PasswordUtil.MD5encrypt(request.getSession().getId());
    }

    /**
     *
     * @param errors
     */
    private void throwException(Errors errors) {
        BusinessException e = new BusinessException(errors);
        //throw e;
    }

}

