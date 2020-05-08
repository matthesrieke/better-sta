package org.n52.bettersta.sta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

abstract class AbstractApplicationTests {
    @Value("${spring.mvc.servlet.path:/}")
    private String servletPath;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void shouldWorkForSimpleValue() {
        validateValue("a");
    }

    @Test
    public void shouldWorkForEncodedValue() {
        validateValue(":");
    }

    @Test
    public void shouldWorkForSlash() {
        validateValue("/");
    }

    @Test
    public void shouldWorkForDoubleSlash() {
        validateValue("//");
    }

    @Test
    public void shouldWorkForEncodedUrl() throws Exception {
        validateValue("http://example.com");
    }

    @Test
    public void shouldWorkForEncodedUrlWithPath() throws Exception {
        validateValue("http://example.com/some/path");
    }

    private void validateValue(String value) {
        String url = (servletPath + "/ObservedProperty({value})").replaceAll("//", "/");
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class, value);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new Result(value)));
    }

}
