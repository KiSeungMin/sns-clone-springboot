package afoc.snsclonespringboot.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberShowForm {
    private Long id;
    private String username;
    private String profileImagePath;
}
