package han.study.hanboard.api.domain.comment.models;

import han.study.hanboard.api.domain.member.models.Member;
import han.study.hanboard.api.domain.post.models.Post;
import han.study.hanboard.api.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Comment(Member member, String content, Post post) {
        this.member = member;
        this.content = content;
        this.post = post;
    }
}

