package pl.programmersrest.blog.controllers.response;

import lombok.*;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.entity.TagDetails;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostWrapper {
    private Post post;
    private List<TagDetails> tagDetailsList;
}
