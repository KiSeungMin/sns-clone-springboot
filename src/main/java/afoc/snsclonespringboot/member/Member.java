package afoc.snsclonespringboot.member;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

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

    @Column(nullable = false, name="email", unique = true)
    private String email;

    @Column(nullable = false ,name="password")
    private String password;

    @Column(nullable = false, name="username")
    private String username;

    private Long imageDataId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(
            String username,
            String password,
            String email,
            Long imageDataId,
            Role role
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageDataId = imageDataId;
        this.role = role;
    }
}

