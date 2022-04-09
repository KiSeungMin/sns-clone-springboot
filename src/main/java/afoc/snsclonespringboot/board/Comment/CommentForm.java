package afoc.snsclonespringboot.board.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentForm {

    private Long boardId;

    private String content;
}
