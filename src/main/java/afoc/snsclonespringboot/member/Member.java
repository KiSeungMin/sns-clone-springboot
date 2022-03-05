package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.member.follow.Follow;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name="username")
    private String username;

    @Column(nullable = false ,name="password")
    private String password;

    @Column(nullable = false, name="nickname")
    private String nickname;

    @Column(nullable = false, name="email")
    private String email;

    @Column(name = "profilepicturepath")
    private String profilePicturePath;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    //@OneToMany()
    //private List<Follow> followerList =  new ArrayList<>();

    //@OneToMany()
    //private List<Follow> followeeList = new ArrayList<>();

    @Builder
    public Member(
            String username,
            String password,
            String nickname,
            String email,
            String profilePicturePath
    ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profilePicturePath = profilePicturePath;
    }
}
