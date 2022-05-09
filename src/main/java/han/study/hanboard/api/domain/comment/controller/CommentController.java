package han.study.hanboard.api.domain.comment.controller;

import han.study.hanboard.api.domain.comment.dto.CommentDto;
import han.study.hanboard.api.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 생성
    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable Long postId, CommentDto commentDto, @SessionAttribute(name = "username", required = false) String username) {
        // 로그인이 안된 사용자면 로그인으로 튕기기
        if (!StringUtils.hasText(username)) {
            return "redirect:/login";
        }

        commentDto.setUsername(username);
        // 게시물 작성용 CommentDto 와 현재 로그인중인 사용자의 이름이 같지 않을 때
        if (!(commentDto.getUsername().equals(username))) {
            throw new IllegalArgumentException("올바르지 않은 경로입니다.");
        }


        commentService.createComment(postId, commentDto);
        return "redirect:/post/{postId}";
    }

    // 수정

    // 삭제
}
