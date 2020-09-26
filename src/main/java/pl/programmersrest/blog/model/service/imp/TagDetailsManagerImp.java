package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.model.entity.TagDetails;
import pl.programmersrest.blog.model.exceptions.custom.TagException;
import pl.programmersrest.blog.model.exceptions.custom.TagNotFoundException;
import pl.programmersrest.blog.model.repository.TagRepository;
import pl.programmersrest.blog.model.service.TagDetailsManager;
import pl.programmersrest.blog.model.util.Validator;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TagDetailsManagerImp implements TagDetailsManager {
    private TagRepository tagRepository;
    private Validator<String> tagNameValidator;

    @Autowired
    public TagDetailsManagerImp(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        tagNameValidator = input -> {
            Pattern pattern = Pattern.compile("[a-zA-z]{2,15}");
            if (pattern.matcher(input).matches()) {
                return true;
            }
            return false;
        };
    }

    @Override
    public List<TagDetails> loadAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<TagDetails> loadTagByName(String name) {
        Optional<TagDetails> tag = tagRepository.findTagByName(name
                .trim()
                .toUpperCase());

        if(!tag.isPresent()){
            throw new TagNotFoundException("There's no tag with this name");
        }
        return tag;
    }

    @Override
    public void updateTagName(String oldName, String newName){
        TagDetails tag = loadTagByName(oldName)
                .orElseThrow(() -> new TagNotFoundException("There's no tag with this name"));

        if (!tagNameValidator.validate(newName)) {
            throw new TagException("Name can contain only letters and size must be between 2 and 15");
        }

        tag.setName(newName.toUpperCase());
        tagRepository.save(tag);
    }

    @Override
    public void createNewTag(String name){
        if (tagExist(name)) {
            throw new TagException("This tag already exist");
        }
        if (!tagNameValidator.validate(name)) {
            throw new TagException("Name can contain only letters and size must be between 2 and 15");
        }

        TagDetails tag = new TagDetails();
        tag.setName(name.toUpperCase());
        tagRepository.save(tag);
    }

    @Override
    public boolean tagExist(String name) {
        return tagRepository.findTagByName(name).isPresent();
    }

    @Override
    public void deleteTag(String name) throws TagNotFoundException {
        TagDetails tag = loadTagByName(name.toUpperCase())
                .orElseThrow(() -> new TagNotFoundException("There's no tag with this name"));

        tagRepository.delete(tag);
    }
}
