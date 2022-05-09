package han.study.hanboard.api.domain.comment.service;

import han.study.hanboard.api.domain.comment.dto.CommentDto;
import han.study.hanboard.api.domain.comment.models.Comment;
import han.study.hanboard.api.domain.member.models.Member;
import han.study.hanboard.api.domain.member.repository.MemberRepository;
import han.study.hanboard.api.domain.post.models.Post;
import han.study.hanboard.api.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void createComment(Long postId, CommentDto commentDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 ID 입니다."));

        Member findMember = memberRepository.findByUsername(commentDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정보입니다."));

        Comment buildComment = Comment.builder()
                .member(findMember)
                .post(findPost)
                .content(commentDto.getContent())
                .build();

        findPost.getComments().add(buildComment);
    }

}
