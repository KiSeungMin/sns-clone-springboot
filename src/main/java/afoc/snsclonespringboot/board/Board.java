package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.boarddata.BoardData;
import afoc.snsclonespringboot.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(length = 2000)
    private String textData;

    private Date date;

    @OneToMany(mappedBy = "board")
    private List<BoardData> boardDataList;

    @Builder
    public Board(
            Member member,
            String textData,
            Date date
    ) {
        this.member = member;
        this.textData = textData;
        this.date = date;
    }
}
