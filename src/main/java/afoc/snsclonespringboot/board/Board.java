package afoc.snsclonespringboot.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "BOARD_ID")
    private Long boardId;

    private Long memberId;

    private Long textDataId;
    private Long imageDataId;

    @Builder
    public Board(
            Long memberId,
            Long textDataId,
            Long imageDataId
    ) {
        this.memberId = memberId;
        this.textDataId = textDataId;
        this.imageDataId = imageDataId;
    }
}
