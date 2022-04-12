package han.study.hanboard.api.domain.member.repository;

import han.study.hanboard.api.domain.member.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}
