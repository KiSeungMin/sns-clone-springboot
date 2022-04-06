package afoc.snsclonespringboot.board;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class BoardForm {

    private Long boardId;

    private String username;

    private Long userId;

    //private Long textDataId;

    //private Long imageDataId;

    private Date date;

    private Boolean followIsPresent;

    private Boolean likeIsPresent;
}
