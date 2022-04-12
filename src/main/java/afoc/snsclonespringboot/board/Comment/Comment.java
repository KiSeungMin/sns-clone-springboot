package afoc.snsclonespringboot.board.Comment;

import afoc.snsclonespringboot.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    private Long id;

    private Long boardId;

    @OneToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    private String content;

    private Date date;

    @Builder
    public Comment(
        Long boardId, Member member, String content, Date date
    ){
        this.boardId=boardId;
        this.member=member;
        this.content=content;
        this.date=date;
    }
}
