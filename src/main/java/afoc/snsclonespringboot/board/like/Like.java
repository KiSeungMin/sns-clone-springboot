package afoc.snsclonespringboot.board.like;

import afoc.snsclonespringboot.board.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@Table(name = "LIKETABLE")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIKE_ID")
    private Long id;

    private Long boardId;

    private Long userId;

    public Like(){

    }

    public Like(Long boardId, Long userId){
        this.boardId = boardId;
        this.userId = userId;
    }
}
