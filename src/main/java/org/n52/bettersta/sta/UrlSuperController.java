package org.n52.bettersta.sta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UrlSuperController {

    private static final Logger log = LoggerFactory.getLogger( UrlSuperController.class );

    @GetMapping(value = "/ObservedProperty({regexEnabledUrl:[^)]+})", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> urlMonster(@PathVariable String regexEnabledUrl) {
        log.info("Nice decoded URL: {}", regexEnabledUrl);

        return Collections.singletonMap("decoded", regexEnabledUrl);
    }
    // http://localhost:8080/ObservedProperty(a) klappt
    // http://localhost:8080/ObservedProperty(https%3A%2F%2Fexample.com%2Fvocab-service%2FOzone%0A)
}
