package han.study.hanboard.api.domain.post.models.dto;

import han.study.hanboard.api.domain.post.models.Post;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    @NotEmpty
    public String title;

    @NotEmpty
    public String content;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
