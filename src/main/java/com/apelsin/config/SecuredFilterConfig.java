package com.apelsin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private JwtFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/articletype/adm/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/article/adm/*");
        bean.addUrlPatterns("/region/adm/*");
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/tag/adm/*");
        return bean;
    }
}
