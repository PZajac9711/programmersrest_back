package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.model.entity.TagDetails;
import pl.programmersrest.blog.model.repository.TagRepository;
import pl.programmersrest.blog.model.service.TagService;

import java.util.List;

@Service
public class TagServiceImp implements TagService {
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImp(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagDetails> loadTagsForPost(long id) {
        return tagRepository.findByPostId(id);
    }
}
