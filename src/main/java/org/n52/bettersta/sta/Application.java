package org.n52.bettersta.sta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

    static {
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "%");
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
        System.setProperty("org.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUrlPathHelper(new CustomUrlPathHelper());
    }
}
