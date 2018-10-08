package com.zhihe.template.interceptor;

import com.zhihe.basic.BusinessException;
import com.zhihe.basic.Errors;
import com.zhihe.template.domain.dataJpa.User;
import com.zhihe.template.service.UserCacheService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccessControlInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserCacheService userCacheService;

    private static final List<String> noLoginResources = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;

        {
            // swagger相关资源不需要登录
            /*add("/swagger-ui.html");
            add("/configuration");
            add("/swagger-resources");
            add("/api-docs");
            add("/v2/api-docs");
            add("/seewo.html");
            add("/web-api");
             add("/error");*/
            add("/");
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 不需要进行访问控制的资源过滤
        String uri = request.getRequestURI();
        for (String resource : noLoginResources) {
            if (uri.startsWith(resource)) {
                return true;
            }
        }
        return hasAccessPermission(handler, request);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 是否有权限访问
     *
     * @param handler
     * @param request
     * @return
     */
    private boolean hasAccessPermission(Object handler, HttpServletRequest request) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            ACS acs = handlerMethod.getMethodAnnotation(ACS.class);
//            // 1、判断是否允许匿名访问
//            if (acs != null) {
//                if (acs.allowAnonymous()) {
//                    return true;
//                }
//            }
            // 2、判断用户是否登录测试时不用登陆
            User user = userCacheService.getCacheUser(request);
            if (user == null) {
                throwException(Errors.SYSTEM_NOT_LOGIN.code,Errors.SYSTEM_NOT_LOGIN.label);
            }
            // 3、判断用户权限

            return true;
        }
        throwException(Errors.ACCESS_NOT_ALLOWED.code, "访问鉴权失败[url" + request.getRequestURI() + "]");
        return false;
    }

    private void throwException(int code, String codeLabel) {
        BusinessException e = new BusinessException(code, codeLabel);
        //throw e;
    }

    private void throwException(Errors error) {
        BusinessException e = new BusinessException(error);
        //throw e;
    }
}
