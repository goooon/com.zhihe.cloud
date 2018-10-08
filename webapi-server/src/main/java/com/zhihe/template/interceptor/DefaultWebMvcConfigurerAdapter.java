package com.zhihe.template.interceptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class DefaultWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 访问权限控制
        AccessControlInterceptor accessControlInterceptor = applicationContext.getBean(AccessControlInterceptor.class);

        registry.addInterceptor(accessControlInterceptor);
    }
}
