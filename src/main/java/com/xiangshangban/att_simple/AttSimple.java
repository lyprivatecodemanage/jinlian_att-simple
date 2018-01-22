package com.xiangshangban.att_simple;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.xiangshangban.att_simple.filter.ServletFilter;


@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class AttSimple {

    public static Logger logger = Logger.getLogger(AttSimple.class);

    public static void main(String[] args) {
        SpringApplication.run(AttSimple.class, args);
        logger.info("启动成功");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        ServletFilter weChatFilter = new ServletFilter();
        registrationBean.setFilter(weChatFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
