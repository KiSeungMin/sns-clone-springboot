package afoc.snsclonespringboot;

import afoc.snsclonespringboot.domain.AccountRole;
import afoc.snsclonespringboot.domain.Member;
import afoc.snsclonespringboot.repository.MemberRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Rollback(false)
public class RepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testMemberRepository(){

        MemberRepositoryImpl memberRepositoryImpl = new MemberRepositoryImpl(em);

        Member member = new Member("KSM", "1234", "ksm", "ss@email.com", "..", AccountRole.MEMBER);
        Member member2 = new Member("sss", "22" ,"ddd", "dd@email.com" , "...", AccountRole.MEMBER);

        memberRepositoryImpl.save(member);
        memberRepositoryImpl.save(member2);

        System.out.println(member.getId());

        //memberRepositoryImpl.deleteByMemberId(2L);
    }

}
