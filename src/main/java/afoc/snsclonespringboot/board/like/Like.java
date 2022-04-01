package afoc.snsclonespringboot.board.like;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@NoArgsConstructor
@Table(name = "LIKETABLE")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    private Long boardId;

    private Long memberId;

    @Builder
    public Like(
            Long boardId,
            Long memberId
    ) {
        this.boardId = boardId;
        this.memberId = memberId;
    }
}
