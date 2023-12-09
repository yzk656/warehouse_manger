package com.pn.config;

import com.pn.filter.LoginCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author: 杨振坤
 * @date: 2023/7/15 19:13
 */

@Configuration
public class FilterConfig {


    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 配置FilterRegistrationBean的bean对象，注册原生Servlet中的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        //创建FilterRegistrationBean的bean对象
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();

        //创建自定义的过滤器
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter();

        //手动注入Redis模板
        loginCheckFilter.setRedisTemplate(redisTemplate);

        //将自定义的过滤器注册到FilterRegistrationBean中
        filterRegistrationBean.setFilter(loginCheckFilter);
        //指定拦截请求
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
