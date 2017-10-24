package cz.edhouse.javaee.jpa;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class EntityManagerProvider implements ServletContextListener, ServletRequestListener {

    private final EntityManagerFactory entityManagerFactory;

    @Resource(lookup = "java:comp/DefaultDataSource")
    private DataSource dataSource;

    public EntityManagerProvider() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initializeDatabaseMigration();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        entityManagerFactory.close();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        final Object entityManager = sre.getServletContext().getAttribute("entityManager");
        if (entityManager != null && entityManager instanceof EntityManager) {
            ((EntityManager) entityManager).close();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletContext().setAttribute("entityManager", entityManagerFactory.createEntityManager());
    }

    private void initializeDatabaseMigration() {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }
}
