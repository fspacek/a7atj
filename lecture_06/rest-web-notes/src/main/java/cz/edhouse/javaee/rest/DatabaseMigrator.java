package cz.edhouse.javaee.rest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Frantisek Spacek
 */
//@Startup/
//@Singleton
public class DatabaseMigrator {

    @Resource(lookup = "java:comp/DefaultDataSource")
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }
}
