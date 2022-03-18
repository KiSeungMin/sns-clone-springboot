package afoc.snsclonespringboot.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

    private String username;

    private String password;

    private String email;
}
