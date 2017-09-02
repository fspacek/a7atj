package cz.edhouse.jdbc.web;

import static cz.edhouse.jdbc.config.Constants.JBDC_URL;
import static cz.edhouse.jdbc.config.Constants.JDBC_PASSWORD;
import static cz.edhouse.jdbc.config.Constants.JDBC_USERNAME;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class DataSourceProvider implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceProvider.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        if (servletContext.getAttribute("dataSource") == null) {
            servletContext.setAttribute("dataSource", createDataSource());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        if (servletContext.getAttribute("dataSource") != null) {
            final Object dsAttribute = servletContext.getAttribute("dataSource");

            if (dsAttribute instanceof JdbcConnectionPool) {
                LOG.info("Data source disposed");
                ((JdbcConnectionPool) dsAttribute).dispose();
            }
        }
    }

    private DataSource createDataSource() {
        LOG.info("Creating data source");
        return JdbcConnectionPool.create(JBDC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

}
