package afoc.snsclonespringboot.board.boarddata;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.data.DataInfo;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table
public class BoardData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @OneToOne
    @JoinColumn(name="DATAINFO_ID")
    private DataInfo dataInfo;
}
