package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.exceptions.custom.TagException;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;

import java.util.List;

public interface TagDetailsManager extends TagDetailsService {
    void updateTagName(String s1,String s2) throws TagException;

    void createNewTag(String s) throws TagException;

    boolean tagExist(String s);

    void deleteTag(String s) throws TagNotFoundException;
}
