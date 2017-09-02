package cz.edhouse.javaee;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class LoggingServletContextListener  implements ServletContextListener{

    private static final Logger LOG = LoggerFactory.getLogger(LoggingServletContextListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         LOG.info("context destroyed");
    }
    
    
}
