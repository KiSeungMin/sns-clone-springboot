package afoc.snsclonespringboot.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowForm {

    private Member member;
    private Boolean followIsPresent;
}
