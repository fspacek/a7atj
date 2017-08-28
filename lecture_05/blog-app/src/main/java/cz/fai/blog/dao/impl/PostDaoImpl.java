package cz.fai.blog.dao.impl;

import cz.fai.blog.dao.PostDao;
import cz.fai.blog.domain.AuthorEntity;
import cz.fai.blog.domain.PostEntity;
import cz.fai.blog.exception.RecordNotFoundException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@RequestScoped
public class PostDaoImpl extends SimpleCrudDao<PostEntity, Integer> implements PostDao {

    @Inject
    public PostDaoImpl(EntityManager entityManager) {
        super(entityManager, PostEntity.class);
    }

    @Override
    public List<PostEntity> getPostsBy(int authorId) {
        return entityManager.createNamedQuery("getByAuthor", PostEntity.class)
                .setParameter("authorId", authorId)
                .getResultList();
    }

    @Override
    public PostEntity createPostFor(int authorId, PostEntity postEntity) {
        final AuthorEntity authorEntity = entityManager.find(AuthorEntity.class, authorId);
        if (authorEntity == null) {
            throw new RecordNotFoundException("author with id %s not found", authorId);
        }

        authorEntity.addPost(postEntity);
        entityManager.persist(authorEntity);

        return postEntity;
    }

    @Override
    public List<PostEntity> fetchAllPublished() {
        return entityManager.createNamedQuery("getAllPublished", PostEntity.class)
                .getResultList();
    }
}
