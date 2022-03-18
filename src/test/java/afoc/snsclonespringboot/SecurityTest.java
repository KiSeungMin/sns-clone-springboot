package afoc.snsclonespringboot;

import afoc.snsclonespringboot.member.JpaMemberRepository;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback(false)
public class SecurityTest {

    //@Autowired
    //MemberServiceImpl memberService;

    @PersistenceContext
    EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){

        Member member = Member.builder()
                .username("12")
                .email("hello@email.com")
                .password("1111")
                .build();

        return member;
    }

    /*
    @Test
    @DisplayName("비밀번호 암호화 테스트")
    public void signUpTest(){

        Member member = createMember();


    }
     */

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){

        MemberServiceImpl memberService = new MemberServiceImpl(new JpaMemberRepository(em));

        Member member1 = createMember();
        Member member2 = createMember();

        memberService.join(member1);
    }
}
