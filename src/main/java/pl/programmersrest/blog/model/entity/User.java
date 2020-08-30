package pl.programmersrest.blog.model.entity;

import lombok.*;
import pl.programmersrest.blog.model.enums.AuthorityEnum;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Column(name = "join_date")
    private Date joinDate;
    private boolean active;
    @Column(name = "not_banned")
    private boolean isNotBanned;
    @Enumerated(EnumType.STRING)
    private AuthorityEnum role;
}
