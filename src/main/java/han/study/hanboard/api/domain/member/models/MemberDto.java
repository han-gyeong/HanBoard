package han.study.hanboard.api.domain.member.models;

import lombok.Data;

@Data
public class MemberDto {

    private String username;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .build();
    }
}
