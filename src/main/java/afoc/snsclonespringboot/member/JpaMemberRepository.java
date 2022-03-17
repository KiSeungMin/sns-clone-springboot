package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.JpaBoardRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository{

    @PersistenceContext
    private EntityManager em;

    public JpaMemberRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Member save(Member member) {

        em.persist(member);

        return member;
    }

    // TODO - 변경
    // member의 boardList에 인수로 받은 board 객체 추가
    // 인수로 받은 board 객체에도 board가 어떤 member 객체의 board인지 세팅
    /*
    public void setBoardItem(Member member, Board board){

//        List<Board> boardList = member.getBoardList();
//
//        boardList.add(board);
//
//        board.setMember(member);
    }
     */

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

        //Member findMember = em.createQuery("select m from Member m where m.email = '"
                //+ email + "'", Member.class).getSingleResult();

        return findMember.stream().findAny();
    }

//    @Override
//    public List<Board> findBoardListByMemberId(Long memberId){
//
//        Member findMember = findMemberByMemberId(memberId).get();
//
//        return findMember.getBoardList();
//    }


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

            JpaBoardRepository jbr = new JpaBoardRepository(em);

            List<Board> findBoard= jbr.findBoardListByMemberId(findMember.get().getId());

            for(Board board : findBoard){
                jbr.deleteBoardByBoardId(board.getBoardId());
            }

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
