package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.exceptions.custom.TagException;

//ToDo: dodawanie/usuwanie nowych tagow do puli, przypisywanie nowych tagow albo usuwanie z posta
public interface TagDetailsManager extends TagDetailsService {
    void updateTagName(String s1,String s2) throws TagException;

    void createNewTag(String s) throws TagException;

    boolean tagExist(String s);
}
