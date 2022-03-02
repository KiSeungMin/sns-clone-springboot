package afoc.snsclonespringboot.controller;

import afoc.snsclonespringboot.domain.AccountRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class MemberForm {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
}
