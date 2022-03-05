package afoc.snsclonespringboot;

import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.JpaMemberRepository;
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

        JpaMemberRepository jpaMemberRepository = new JpaMemberRepository(em);

        Member member = new Member("KSM", "1234", "ksm", "ss@email.com", "..", AccountRole.MEMBER);
        Member member2 = new Member("sss", "22" ,"ddd", "dd@email.com" , "...", AccountRole.MEMBER);

        jpaMemberRepository.save(member);
        jpaMemberRepository.save(member2);

        System.out.println(member.getId());

        //memberRepositoryImpl.deleteByMemberId(2L);
    }

}
