package afoc.snsclonespringboot.board.boarddata;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table
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
