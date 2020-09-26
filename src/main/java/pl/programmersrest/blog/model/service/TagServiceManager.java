package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.exceptions.custom.TagException;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;

/**
 * Provides Tag attach/detach operations from posts.
 *
 * @author Patryk
 */
public interface TagServiceManager extends TagService {
    /**
     * Method assign a tag to a specific post
     *
     * @param id Specified post to which should tag be attached.
     * @param s  Specified tag name
     * @throws TagNotFoundException Throws this exception when Tag which name dont exists in database
     * @throws TagException         Throws this exception when Tag which this name already is attached.
     */
    void assignTagToPost(long id, String s) throws TagNotFoundException, TagException;
}
