package afoc.snsclonespringboot.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
}
