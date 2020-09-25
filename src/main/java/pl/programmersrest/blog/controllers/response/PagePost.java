package pl.programmersrest.blog.controllers.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
public class PagePost {
    private Long id;
    private String imagePath;
    private String title;
    private LocalDateTime date;
    private String author;
    private String shortDescription;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagePost pagePost = (PagePost) o;
        return Objects.equals(id, pagePost.id) &&
                Objects.equals(title, pagePost.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
