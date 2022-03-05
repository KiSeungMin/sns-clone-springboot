package afoc.snsclonespringboot.board.like;

import afoc.snsclonespringboot.board.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@Table(name = "LIKE")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIKE_ID")
    private Long id;

    private Long boardId;

    private Long userId;

    // private Board board;
}
