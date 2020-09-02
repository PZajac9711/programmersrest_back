package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Post;

import java.util.List;

public interface PostService {
    List<PagePost> getPosts(int page);

    Post getSpecificPost(long id);

    void updateSpecificPost(long id, UpdatePostRequest updatePostRequest);

    void deletePost(long id);

    void createPost(String username, CreatePostRequest createPostRequest);

    void updateTitle(Long id, String title);

    void updateShortDescription(long id, String shortDescription);

    void updateFullDescription(long id, String fullDescription);

    void updateImagePath(long id, String imagePath);

    void changePostStatus(long id);
}
