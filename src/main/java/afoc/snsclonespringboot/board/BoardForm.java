package afoc.snsclonespringboot.board;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardForm {

    private Long boardId;

    private String username;

    private LocalDateTime regTime;

    private Boolean likeIsPresent;
}
