package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.exceptions.custom.TagException;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;

import java.util.List;

/**
 * Provide basic operation on Tags.
 *
 * @author Patryk
 */
public interface TagDetailsManager extends TagDetailsService {
    /**
     * Update exists tag by changing it name.
     *
     * @param s1 Specified old Tag name.
     * @param s2 Specified new Tag name.
     * @throws TagException         when Tag name didn't match pattern.
     * @throws TagNotFoundException when Tag is not present in database.
     */
    void updateTagName(String s1, String s2) throws TagException, TagNotFoundException;

    /**
     * Create a new tag that can be assigned to a posts
     *
     * @param s Specified Tag name.
     * @throws TagException when name didn't match to pattern or Tag which this name already exists.
     */
    void createNewTag(String s) throws TagException;

    /**
     * Check if tag which specified name exists in database
     *
     * @param s Specified Tag name
     * @return true or false depends if tag is present in database
     */
    boolean tagExist(String s);

    /**
     * Delete tag from database
     *
     * @param s Specified Tag name.
     * @throws TagNotFoundException when Tag which this name is not present in database.
     */
    void deleteTag(String s) throws TagNotFoundException;
}
