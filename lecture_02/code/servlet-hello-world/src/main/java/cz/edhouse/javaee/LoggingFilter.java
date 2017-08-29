package cz.edhouse.javaee;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Frantisek Spacek
 */
@WebFilter(filterName = "LoggingFilter", urlPatterns = {"/*"})
public class LoggingFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doBeforeProcessing(request, response);
        chain.doFilter(request, response);
        doAfterProcessing(request, response);
    }

    @Override
    public void destroy() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        LOG.info("Recieved request from {}", request.getRemoteAddr());

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
       LOG.info("End of servering request from {}", request.getRemoteAddr());
    }
}
