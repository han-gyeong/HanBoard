package han.study.hanboard.api.domain.member.service;

import han.study.hanboard.api.domain.member.models.Member;
import han.study.hanboard.api.domain.member.models.MemberDto;
import han.study.hanboard.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void createMember(MemberDto memberDto) {
        Member member = memberDto.toEntity();
        memberRepository.save(member);
    }

    public boolean login(MemberDto memberDto) {
        Member findMember = memberRepository.findByUsername(memberDto.getUsername());
        if (findMember == null) {
            return false;
//            throw new IllegalArgumentException();
        }

        return findMember.getPassword().equals(memberDto.getPassword());
    }
}
