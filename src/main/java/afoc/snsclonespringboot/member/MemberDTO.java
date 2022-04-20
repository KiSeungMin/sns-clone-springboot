package afoc.snsclonespringboot.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberDTO {
    private Long id;
    private String username;
    private String profileImgPath;
}
