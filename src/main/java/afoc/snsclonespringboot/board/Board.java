package afoc.snsclonespringboot.board;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime regTime;

    @Builder
    public Board(
            Long memberId,
            LocalDateTime regTime
    ) {
        this.memberId = memberId;
        this.regTime = regTime;
    }
}
