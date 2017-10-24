package cz.edhouse.javaee.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class DataSourceProvider implements ServletContextListener {

    private final DataSource dataSource;

    public DataSourceProvider() {
        this.dataSource = createDataSource();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initializeDatabaseMigration();
        sce.getServletContext().setAttribute("dataSource", dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void initializeDatabaseMigration() {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    private DataSource createDataSource() {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:notes");
        return new HikariDataSource(hikariConfig);
    }
}
