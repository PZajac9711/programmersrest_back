package pl.programmersrest.blog.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
}
