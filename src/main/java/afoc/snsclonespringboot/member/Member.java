package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.data.DataInfo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToOne
    @JoinColumn(name = "DATAINFO_ID")
    private DataInfo dataInfo;

    @Enumerated(EnumType.STRING)
    private Role role;
}

