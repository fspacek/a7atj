package cz.edhouse.jpa.web;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class EntityManagerFactoryProvider implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();
        if (context.getAttribute("entityManagerFactory") == null) {
            context.setAttribute("entityManagerFactory", createEntityManagerFactory());
        }
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("default-unit");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();
        if (context.getAttribute("entityManagerFactory") != null) {
            ((EntityManagerFactory) context.getAttribute("entityManagerFactory")).close();
        }
    }

}
