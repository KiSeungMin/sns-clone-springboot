package afoc.snsclonespringboot.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@Builder
public class BoardContentDTO {
    private BoardDTO board;
    private Boolean followIsPresent;
    private Boolean likeIsPresent;
}
