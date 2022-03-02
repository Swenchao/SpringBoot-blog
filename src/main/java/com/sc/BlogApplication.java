package com.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.domain.Specification;

//@SpringBootApplication
//public class BlogApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(BlogApplication.class, args);
//    }
//
//}

/**
 * @author swenchao
 * 打war包，使用外置tomcat部署
 */
@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BlogApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
