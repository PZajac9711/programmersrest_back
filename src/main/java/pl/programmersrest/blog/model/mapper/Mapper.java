package pl.programmersrest.blog.model.mapper;

public interface Mapper<F,T>{
    T converter(F from);
}
