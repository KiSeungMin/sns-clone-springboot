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

    @Column(nullable = false, name="email")
    private String email;

    @Column(nullable = false ,name="password")
    private String password;

    @Column(nullable = false, name="username")
    private String username;

    // TODO
    @Column(name = "profilepicturepath")
    private String profilePicturePath;
//    private FileClass Image
//    private FileClass 글;


    @Builder
    public Member(
            String username,
            String password,
            String email,
            String profilePicturePath
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilePicturePath = profilePicturePath;
    }
}
