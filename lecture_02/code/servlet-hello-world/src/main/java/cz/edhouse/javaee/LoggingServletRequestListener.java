package cz.edhouse.javaee;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class LoggingServletRequestListener implements ServletRequestListener {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingServletRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        LOG.info("Request destroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        LOG.info("Request initialized");
    }

}
