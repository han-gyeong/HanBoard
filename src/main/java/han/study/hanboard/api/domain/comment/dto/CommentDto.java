package han.study.hanboard.api.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

// 댓글용 DTO
@Getter
@Setter
public class CommentDto {
    String username;
    String content;
}
