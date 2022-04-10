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

    private String username;

    private String textData;

    private Long imageDataId;

    private Date date;

    @Builder
    public Board(
            Long memberId,
            String username,
            String textData,
            Long imageDataId,
            Date date
    ) {
        this.memberId = memberId;
        this.username = username;
        this.textData = textData;
        this.imageDataId = imageDataId;
        this.date = date;
    }
}
