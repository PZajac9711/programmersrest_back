package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.entity.TagDetails;

import java.util.List;

public interface TagService {
    List<TagDetails> loadTagsForPost(long id);
}
