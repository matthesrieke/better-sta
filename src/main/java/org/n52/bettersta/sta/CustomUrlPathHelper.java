package org.n52.bettersta.sta;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomUrlPathHelper extends ExtendableUrlPathHelper {
    private static final String SLASH = "/";
    private static final String ENCODED_SLASH = UriUtils.encode(SLASH, StandardCharsets.UTF_8);
    private static final String DOUBLE_ENCODED_SLASH = UriUtils.encode(ENCODED_SLASH, StandardCharsets.UTF_8);
    private static final Pattern ENCODED_SLASH_PATTERN = Pattern.compile(ENCODED_SLASH,
                                                                         Pattern.CASE_INSENSITIVE | Pattern.LITERAL);

    @Override
    public String decodeRequestString(HttpServletRequest request, String source) {
        String[] split = source.split("\\?", 2);
        split[0] = ENCODED_SLASH_PATTERN.matcher(split[0]).replaceAll(DOUBLE_ENCODED_SLASH);
        return super.decodeRequestString(request, split.length <= 1 ? split[0] : split[0] + '?' + split[1]);
    }

    @Override
    public String getServletPath(HttpServletRequest request) {
        String servletPath = getSanitizedPath(super.getServletPath(request));
        String contextPath = getContextPath(request);
        String requestUri = getSanitizedPath(super.decodeRequestString(request, removeSemicolonContent(getRequestUri(request))));
        String pathWithinApplication = getRemainingPath(requestUri, contextPath, true);
        if (pathWithinApplication != null) {
            // Normal case: URI contains context path.
            pathWithinApplication = StringUtils.hasText(pathWithinApplication) ? pathWithinApplication : SLASH;
        } else {
            pathWithinApplication = requestUri;
        }
        return servletPath.equals(pathWithinApplication) ? "" : servletPath;
    }

    @Override
    public Map<String, String> decodePathVariables(HttpServletRequest request, Map<String, String> vars) {
        Map<String, String> decodedVars = new LinkedHashMap<>(vars.size());
        vars.forEach((key, value) -> decodedVars.put(key, ENCODED_SLASH_PATTERN.matcher(value).replaceAll(SLASH)));
        return super.decodePathVariables(request, decodedVars);
    }
}
