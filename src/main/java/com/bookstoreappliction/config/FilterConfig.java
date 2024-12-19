package com.bookstoreappliction.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistration(TokenUtility tokenUtility) {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilter(tokenUtility));
        registrationBean.addUrlPatterns("/user/*"); // Apply the filter to any endpoint under /api
        registrationBean.addUrlPatterns("/book/*");
        registrationBean.addUrlPatterns("/cart/*");
        registrationBean.addUrlPatterns("/order/*");
        return registrationBean;
    }
}
