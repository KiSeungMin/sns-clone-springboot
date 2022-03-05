package afoc.snsclonespringboot.board.like;

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
    private Long id;

    private Long boardId;

    private Long userId;
}
