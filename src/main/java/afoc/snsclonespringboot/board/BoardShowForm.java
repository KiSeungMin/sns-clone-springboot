package afoc.snsclonespringboot.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
@Builder
public class BoardShowForm {
    private Long boardId;
    private Long memberId;
    private String username;
    private Date date;
    private String textData;
    private List<String> imgPath;
}
