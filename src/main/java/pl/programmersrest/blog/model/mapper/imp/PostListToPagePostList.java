package pl.programmersrest.blog.model.mapper.imp;

import org.springframework.stereotype.Component;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostListToPagePostList implements Mapper<List<Post>, List<PagePost>> {
    @Override
    public List<PagePost> converter(List<Post> from) {
        return from.stream()
                .map(post -> PagePost.builder()
                        .author(post.getAuthor())
                        .date(post.getCreateDate())
                        .imagePath(post.getImaginePath())
                        .shortDescription(post.getShortDescription())
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());
    }
}
