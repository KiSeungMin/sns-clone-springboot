package afoc.snsclonespringboot.board;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class BoardData {
    @Id
    private Long id;

    private Long boardId;

    private Long dataInfoId;

    @Builder
    public BoardData(
            Long boardId,
            Long dataInfoId
    ) {
        this.boardId = boardId;
        this.dataInfoId = dataInfoId;
    }
}
