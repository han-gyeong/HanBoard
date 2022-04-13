package han.study.hanboard.api.domain.post.repository;

import han.study.hanboard.api.domain.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findByMember(Member member);
}
