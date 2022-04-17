package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.boarddata.BoardData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardContentForm {

    private Board board;

    private BoardData boardData;

    private Boolean followIsPresent;

    private Boolean likeIsPresent;
}
