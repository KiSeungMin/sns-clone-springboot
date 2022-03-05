package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
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

    public Member(){

    }

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
