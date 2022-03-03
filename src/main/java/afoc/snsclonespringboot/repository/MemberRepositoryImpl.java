package afoc.snsclonespringboot.repository;

import afoc.snsclonespringboot.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    @PersistenceContext
    private EntityManager em;

    public MemberRepositoryImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Member save(Member member) {

        em.persist(member);

        return member;
    }

    @Override
    public Optional<Member> findByMemberId(Long MemberId) {

        Member findMember = em.find(Member.class, MemberId);

        return Optional.of(findMember);
    }

    @Override
    public Optional<Member> findByMemberUsername(String username) {

        Member findMember = em.find(Member.class, username);

        return Optional.of(findMember);
    }

    @Override
    public Optional<Member> updateByMemberId(Long memberId) {

        Member findMember = em.find(Member.class, memberId);

        // update

        return Optional.of(findMember);
    }

    @Override
    public Boolean deleteByMemberId(Long memberId) {

        if(em.find(Member.class, memberId) != null){
            em.remove(em.find(Member.class, memberId));
            return true;
        }
        return false;
    }
}
