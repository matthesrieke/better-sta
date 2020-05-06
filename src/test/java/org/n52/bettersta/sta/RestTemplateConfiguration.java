package org.n52.bettersta.sta;

import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Optional;

@Configuration
class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(
            Environment environment, Optional<AbstractServletWebServerFactory> servletWebServerFactory) {
        return new RestTemplateBuilder()
                       .uriTemplateHandler(uriTemplateHandler(environment, servletWebServerFactory));
    }

    private LocalHostUriTemplateHandler uriTemplateHandler(
            Environment environment, Optional<AbstractServletWebServerFactory> servletWebServerFactory) {
        return new LocalHostUriTemplateHandler(environment, getScheme(servletWebServerFactory), uriBuilderFactory());
    }

    private DefaultUriBuilderFactory uriBuilderFactory() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return uriBuilderFactory;
    }

    private String getScheme(Optional<AbstractServletWebServerFactory> servletWebServerFactory) {
        return isSslEnabled(servletWebServerFactory) ? "https" : "http";
    }

    private boolean isSslEnabled(Optional<AbstractServletWebServerFactory> servletWebServerFactory) {
        return servletWebServerFactory.map(x -> x.getSsl() != null && x.getSsl().isEnabled()).orElse(false);
    }
}
