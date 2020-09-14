package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;

public interface PostServiceManager extends PostService{
    void updateSpecificPost(long id, UpdatePostRequest updatePostRequest);

    void deletePost(long id);

    void createPost(String username, CreatePostRequest createPostRequest);

    void changePostStatus(long id);
}
