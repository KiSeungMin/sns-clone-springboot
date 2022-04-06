package afoc.snsclonespringboot.board.like;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "LIKETABLE")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    private Long boardId;

    private Long memberId;
}
