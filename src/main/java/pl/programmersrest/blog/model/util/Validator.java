package pl.programmersrest.blog.model.util;

@FunctionalInterface
public interface Validator<T> {
    boolean validate(T obj);
}
