package afoc.snsclonespringboot.member;

import lombok.*;

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

    @Column(nullable = false, name="email")
    private String email;

    @Column(nullable = false ,name="password")
    private String password;

    @Column(nullable = false, name="username")
    private String username;

    private Long imageDataId;

    @Builder
    public Member(
            String username,
            String password,
            String email,
            Long imageDataId
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageDataId = imageDataId;
    }
}

