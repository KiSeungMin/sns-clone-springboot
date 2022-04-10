package afoc.snsclonespringboot.board;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BoardContentForm {

    private Board board;

    private Boolean followIsPresent;

    private Boolean likeIsPresent;
}
