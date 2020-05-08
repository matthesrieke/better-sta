package org.n52.bettersta.sta;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    private final ErrorProperties errorProperties;

    public ErrorController(ErrorAttributes errorAttributes, ServerProperties errorProperties) {
        super(errorAttributes, Collections.emptyList());
        this.errorProperties = errorProperties.getError();
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(getErrorAttributes(request, isIncludeStackTrace(request)), status);
    }

    private boolean isIncludeStackTrace(HttpServletRequest request) {
        return errorProperties.getIncludeStacktrace() == IncludeStacktrace.ALWAYS ||
               (errorProperties.getIncludeStacktrace() == IncludeStacktrace.ON_TRACE_PARAM
                && getTraceParameter(request));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
        return ResponseEntity.status(getStatus(request)).build();
    }
}