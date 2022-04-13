package han.study.hanboard.api.domain.post.models;

import han.study.hanboard.api.domain.comment.models.Comment;
import han.study.hanboard.api.domain.member.models.Member;
import han.study.hanboard.api.domain.post.models.dto.PostDto;
import han.study.hanboard.api.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void update(PostDto postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.setModifiedTime(LocalDateTime.now());
    }
}
