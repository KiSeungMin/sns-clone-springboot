package afoc.snsclonespringboot.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private Long id;
    private String email;
    private String password;
    private String username;
}
