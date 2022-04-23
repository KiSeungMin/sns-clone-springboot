package afoc.snsclonespringboot.member.follow;

import afoc.snsclonespringboot.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowDto {

    private Member member;
    private Boolean followIsPresent;
}
