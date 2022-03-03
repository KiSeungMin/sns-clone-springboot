package afoc.snsclonespringboot.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "MEMBER")
public class Member implements UserDetails {

    @Id
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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Builder
    public Member(
            String username,
            String password,
            String nickname,
            String email,
            String profilePicturePath,
            AccountRole role
    ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profilePicturePath = profilePicturePath;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AccountRole.MEMBER.getValue()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
