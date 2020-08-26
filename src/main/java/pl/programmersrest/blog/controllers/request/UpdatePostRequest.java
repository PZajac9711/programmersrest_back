package pl.programmersrest.blog.controllers.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@Builder
@ToString
public class UpdatePostRequest {
    private String title;
    private String shortDescription;
    private String fullDescription;

    public UpdatePostRequest(String title, String shortDescription, String fullDescription) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
    }

    public UpdatePostRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePostRequest that = (UpdatePostRequest) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(fullDescription, that.fullDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortDescription, fullDescription);
    }
}
