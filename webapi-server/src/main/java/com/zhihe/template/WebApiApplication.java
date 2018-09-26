package com.zhihe.template;

import com.fasterxml.classmate.TypeResolver;
import com.zhihe.template.config.AppConfig;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.time.LocalDate;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EnableFeignClients //1负载均衡
@EnableCircuitBreaker //2断路器
public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebApiApplication.class);
        app.run(args);
    }

    @Bean
    @LoadBalanced //开启均衡负载能力
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Resource
    private AppConfig appConfig;

    @Autowired
    private TypeResolver typeResolver;

//	  @Bean//错误页面访问
//	  public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//	     return (container -> {
//	          ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/index.html");//401
//	          ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");//404
//	          ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/index.html");//500
//
//	          container.addErrorPages(error401Page, error404Page, error500Page);
//	     });
//	  }

    /**
     * 生成API文档的入口
     */
    @Bean
    public Docket generateApi() {
        Docket docket = null;
        // 可以根据配置决定不做任何API生成
        if (appConfig.getSwaggerShow() == 1) {
            docket = new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.none())
                    .build();
        }

        docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 标示只有被 @Api 标注的才能生成API.
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                // 遇到 LocalDate时，输出成String
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(
                                typeResolver.resolve(DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false);
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("模板- RESTFul apis")
                .description("主要提供统一对外接口访问")
                .termsOfServiceUrl("仅供研发人员使用，内部资料！！")
                .contact("提供者：guzhihe")
                .version("V.1.0")
                .build();
    }
}
