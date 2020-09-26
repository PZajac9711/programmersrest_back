package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.TitleTakenException;

/**
 * Provides basic operation on Posts
 *
 * @author Patryk
 */
public interface PostServiceManager extends PostService {
    /**
     * Update existing post. note that only user with ADMIN authority should be able to do this.
     *
     * @param id                Specified post which are we trying to update
     * @param updatePostRequest Specified new values for the post
     * @throws PostNotFoundException when post which this id is not present in database
     * @throws TitleTakenException   when post which the same title already exists in database
     */
    void updateSpecificPost(long id, UpdatePostRequest updatePostRequest) throws PostNotFoundException, TitleTakenException;

    /**
     * Delete existing post. note that only user with ADMIN authority should be able to do this.
     *
     * @param id Specified post which are we trying to delete.
     * @throws PostNotFoundException when post is not present in database.
     */
    void deletePost(long id) throws PostNotFoundException;

    /**
     * Create a new post, note that title for posts need to be unique.
     *
     * @param username          Specified user who create the post.
     * @param createPostRequest Specified details about post.
     * @throws TitleTakenException when post with the same title already exist in database.
     */
    void createPost(String username, CreatePostRequest createPostRequest) throws TitleTakenException;

    /**
     * Change post status from true to false and vice versa, when post status is false it's should not be listed for normal users.
     *
     * @param id Specified which post should we change the status
     * @throws PostNotFoundException when post is not present in database
     */
    void changePostStatus(long id) throws PostNotFoundException;
}
