package eu.edhouse.javaee.notes.business;

import eu.edhouse.javaee.notes.security.HashGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Frantisek Spacek
 */
@Stateless
public class OwnerManager {

    private EntityManager entityManager;
    private HashGenerator hashGenerator;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Inject
    public void setHashGenerator(HashGenerator hashGenerator) {
        this.hashGenerator = hashGenerator;
    }

    public void createUser(Owner user) {
        user.setPassword(hashGenerator.getHashText(user.getPassword()));
        entityManager.persist(user);
    }

    public Owner getUserByName(String name) {
        try {
            return entityManager.createNamedQuery("Owner.getByName", Owner.class)
                    .setParameter("username", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }


}
