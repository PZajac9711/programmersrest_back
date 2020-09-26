package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.entity.TagDetails;

import java.util.List;

/**
 * Provides information about assigned task to a specific post
 *
 * @author Patryk
 */
public interface TagService {
    /**
     * Method return all tags that are assigned to this post, notice list can be empty
     *
     * @param id Specified post to which tag should be load
     * @return List of Tag Details attached to a post
     */
    List<TagDetails> loadTagsForPost(long id);
}
