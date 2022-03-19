package afoc.snsclonespringboot.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor
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
