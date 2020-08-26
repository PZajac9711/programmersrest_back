package pl.programmersrest.blog.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Table(name = "subcomment")
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "author")
    private String author;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    private String description;

    public SubComment() {
    }
}
