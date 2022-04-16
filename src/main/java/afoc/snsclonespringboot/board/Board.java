package afoc.snsclonespringboot.board;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    private Long memberId;

    @Column(length = 2000)
    private String textData;

    private Date date;

    @Builder
    public Board(
            Long memberId,
            String textData,
            Date date
    ) {
        this.memberId = memberId;
        this.textData = textData;
        this.date = date;
    }
}
