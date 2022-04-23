package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.member.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
@Builder
public class BoardDTO {
    private Long boardId;
    private MemberDTO writer;
    private Date date;
    private String textData;
    private List<String> imgPath;
}
