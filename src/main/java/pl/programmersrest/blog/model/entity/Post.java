package pl.programmersrest.blog.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "full_description")
    private String fullDescription;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "author")
    private String author;
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
    @Column(name = "imagine_path")
    private String imaginePath;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Comment> commentList;

    public Post() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) &&
                title.equals(post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
