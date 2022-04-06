package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Member save(Member member) {

        em.persist(member);

        return member;
    }

    // memberId를 이용해 member 서칭
    @Override
    public Optional<Member> findMemberByMemberId(Long MemberId) {

        Member findMember = em.find(Member.class, MemberId);

        return Optional.ofNullable(findMember);
    }

    @Override
    public Optional<Member> findMemberByMemberEmail(String email){

        List<Member> findMember = em.createQuery("select m from Member m where m.email = :email",
                Member.class)
                .setParameter("email", email)
                .getResultList();

        return findMember.stream().findAny();
    }

    // TODO
    @Override
    public Boolean updateMember(Member member) {

//        Member findMember = findMemberByMemberId(memberId).get();
//
//        // update
//        return Optional.of(findMember);
        return false;
    }

    @Override
    public Boolean deleteMemberByMemberId(Long memberId) {

        Optional<Member> findMember = findMemberByMemberId(memberId);

        if(findMember.isPresent()){

            em.remove(findMember.get());

            return true;
        }

        return false;
    }

    @Override
    public void clear(){
        em.flush();
    }
}
