package cz.edhouse.jpa.web;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Frantisek Spacek
 */
@WebListener
public class EntityManagerProvider implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        final ServletContext context = sre.getServletContext();
        if (context.getAttribute("entityManager") != null) {
            ((EntityManager) context.getAttribute("entityManager")).close();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        final ServletContext context = sre.getServletContext();
        if (context.getAttribute("entityManagerFactory") != null) {
            final EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context
                    .getAttribute("entityManagerFactory");
            context.setAttribute("entityManager", entityManagerFactory.createEntityManager());
        }
    }

}
