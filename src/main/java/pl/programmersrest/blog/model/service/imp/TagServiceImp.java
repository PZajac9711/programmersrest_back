package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.model.entity.TagDetails;
import pl.programmersrest.blog.model.exceptions.custom.TagException;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;
import pl.programmersrest.blog.model.repository.TagRepository;
import pl.programmersrest.blog.model.service.TagDetailsManager;
import pl.programmersrest.blog.model.service.TagService;
import pl.programmersrest.blog.model.service.TagServiceManager;

import java.util.List;

@Service
public class TagServiceImp implements TagServiceManager {
    private TagRepository tagRepository;
    private TagDetailsManager tagDetailsManager;

    @Autowired
    public TagServiceImp(TagRepository tagRepository, TagDetailsManager tagDetailsManager) {
        this.tagRepository = tagRepository;
        this.tagDetailsManager = tagDetailsManager;
    }

    @Override
    public List<TagDetails> loadTagsForPost(long id) {
        return tagRepository.findByPostId(id);
    }

    @Override
    public void assignTagToPost(long id, String name) {
        TagDetails tagDetails = tagDetailsManager.loadTagByName(name.toUpperCase())
                .orElseThrow(() -> new TagNotFoundException("There's not tag with this name"));
        List<TagDetails> tagDetailsList = tagRepository.findByPostId(id);

        if(tagDetailsList.contains(tagDetails)){
            throw new TagException("This Tag is already assigned to this post");
        }

        tagRepository.assignTagToPost(tagDetails.getId(), id);
    }
}
