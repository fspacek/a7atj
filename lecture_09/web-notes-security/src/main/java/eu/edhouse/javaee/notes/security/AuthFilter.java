package eu.edhouse.javaee.notes.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Frantisek Spacek
 */
@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays
            .asList("login", "registration", "assets")));

    private String contextPath;
    private String loginUrl;

    @Override
    public void init(FilterConfig filterConfig) {
        contextPath = filterConfig.getServletContext().getContextPath();
        loginUrl = contextPath + "/login";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String extractedPath = getHttpRequest(request).getRequestURI().replace(contextPath, "");
        System.out.println(extractedPath);

        final boolean loggedIn = (getHttpRequest(request).getRemoteUser() != null);
        // check if the URL was appointed to login URL or not
        final boolean loginRequest = getHttpRequest(request).getRequestURI().equals(loginUrl);
        // check if the URL was allowed or not
        final boolean allowedUrl = checkIfIsAllowedPath(extractedPath);
        if (loggedIn || loginRequest || allowedUrl) {
            request.setAttribute("logged", loggedIn);
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("login");
        }
    }

    private boolean checkIfIsAllowedPath(String extractedPath) {
        if (ALLOWED_PATHS.contains(extractedPath)) {
            return true;
        }

        for (String path : ALLOWED_PATHS) {
            if (extractedPath.contains(path)) {
                return true;
            }
        }
        return false;
    }

    private HttpServletRequest getHttpRequest(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    @Override
    public void destroy() {
    }

}
