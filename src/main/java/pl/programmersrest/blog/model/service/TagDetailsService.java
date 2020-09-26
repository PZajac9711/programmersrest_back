package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.entity.TagDetails;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Provides basic information about available tags.
 *
 * @author Patryk
 */
public interface TagDetailsService {
    /**
     * Method returns all available tags that we can assign to a post.
     *
     * @return All tags from database, list can be empty.
     */
    List<TagDetails> loadAllTags();

    /**
     * Load a specific tag from database by name
     *
     * @param s Specified tag which should be select from database
     * @return Optional of TagDetails
     * @throws TagNotFoundException Throws when post which name not exists in database
     */
    Optional<TagDetails> loadTagByName(String s) throws TagNotFoundException;
}
