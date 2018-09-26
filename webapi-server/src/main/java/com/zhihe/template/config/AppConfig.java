package com.zhihe.template.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class AppConfig {
    /***
     * 0 api接口显示 1 不显示
     */
    private int swaggerShow;

    public int getSwaggerShow() {
        return swaggerShow;
    }

    public void setSwaggerShow(int swaggerShow) {
        this.swaggerShow = swaggerShow;
    }


}
