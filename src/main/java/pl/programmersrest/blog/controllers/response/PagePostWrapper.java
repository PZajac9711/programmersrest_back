package pl.programmersrest.blog.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.programmersrest.blog.model.entity.TagDetails;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PagePostWrapper {
    private PagePost pagePost;
    private List<TagDetails> tagDetailsList;
}
