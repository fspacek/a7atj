package cz.fai.blog.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents model for post table.
 *
 * @author Frantisek Spacek
 */
@Entity(name = "Post")
@Table(name = "POST")
@NamedQueries({
        @NamedQuery(name = "getByAuthor", query = "select p from Post p where p.author.id = :authorId"),
        @NamedQuery(name = "getAllPublished", query = "select p from Post p where p.published = true")
})
public class PostEntity extends BaseEntity<Integer> {

    private String title;
    private String content;
    private Date createDate;
    private boolean published;
    private AuthorEntity author;
    private Set<TagEntity> tags;

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "published", nullable = false)
    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "tag_id"}))
    public Set<TagEntity> getTags() {
        if (tags == null) {
            this.tags = new LinkedHashSet<>();
        }
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }
}
