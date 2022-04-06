package afoc.snsclonespringboot.board;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter @Getter
@NoArgsConstructor
@ToString
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    private Long memberId;

    private Long textDataId;
    private Long imageDataId;

    private Date date;

    @Builder
    public Board(
            Long memberId,
            Long textDataId,
            Long imageDataId,
            Date date
    ) {
        this.memberId = memberId;
        this.textDataId = textDataId;
        this.imageDataId = imageDataId;
        this.date = date;
    }
}
