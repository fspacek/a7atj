package cz.edhouse.javaee.ioc;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Frantisek Spacek
 */
@Stateless
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
