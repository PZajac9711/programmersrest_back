package pl.programmersrest.blog.controllers.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    private String title;
    private String shortDescription;
    private String fullDescription;
    private String imaginePath;
}
