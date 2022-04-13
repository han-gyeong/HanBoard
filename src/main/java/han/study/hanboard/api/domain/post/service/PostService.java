package han.study.hanboard.api.domain.post.service;

import han.study.hanboard.api.domain.member.models.Member;
import han.study.hanboard.api.domain.member.repository.MemberRepository;
import han.study.hanboard.api.domain.post.models.Post;
import han.study.hanboard.api.domain.post.models.dto.PostDto;
import han.study.hanboard.api.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long createPost(Post post, String username) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new IllegalArgumentException("게시물을 작성하는 회원을 조회할 수 없습니다.");
        }

        post.setMember(member);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public Long editPost(Long id, PostDto postDto) {
        Post findPost = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        findPost.update(postDto);
        return id;
    }
}
