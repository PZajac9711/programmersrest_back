package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Post;

import java.util.List;

/**
 * Provides core information about post.
 *
 * @author Patryk
 */
public interface PostService {
    /**
     * Return list of posts for a single page can return empty list.
     * if list is empty that mean's there's no more pages
     *
     * @param page Specified page number.
     * @return list of posts sorted by id DESC
     */
    List<PagePost> getPosts(int page);

    /**
     * Return information about specific post from database including comments and sub comments
     *
     * @param id Specified post which should be selected from database.
     * @return single post from database.
     */
    Post getSpecificPost(long id);
}
