package org.n52.bettersta.sta;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/ObservedProperty({regexEnabledUrl:[^)]+})", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result urlMonster(@PathVariable String regexEnabledUrl) {
        return new Result(regexEnabledUrl);
    }

}
