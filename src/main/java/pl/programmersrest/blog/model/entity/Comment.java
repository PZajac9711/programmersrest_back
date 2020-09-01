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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "author")
    private String author;
    private String description;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    private Long score;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private List<SubComment> subCommentList;

    public Comment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(postId, comment.postId) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(description, comment.description) &&
                Objects.equals(createDate, comment.createDate) &&
                Objects.equals(score, comment.score) &&
                Objects.equals(subCommentList, comment.subCommentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, author, description, createDate, score, subCommentList);
    }
}
