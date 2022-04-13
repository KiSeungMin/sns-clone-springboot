package afoc.snsclonespringboot.board;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private String username;

    private String textData;

    private Date date;

    @Builder
    public Board(
            Long memberId,
            String username,
            String textData,
            Date date
    ) {
        this.memberId = memberId;
        this.username = username;
        this.textData = textData;
        this.date = date;
    }
}
