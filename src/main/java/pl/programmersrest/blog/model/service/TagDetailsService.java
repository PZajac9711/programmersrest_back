package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.entity.TagDetails;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TagDetailsService {
    List<TagDetails> loadAllTags();

    Optional<TagDetails> loadTagByName(String s) throws TagNotFoundException;
}
