package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaBoardRepository implements BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaBoardRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Board save(Board board) {
        em.persist(board);

        return board;
    }

    @Override
    public Optional<Board> findBoardByBoardId(Long boardId) {

        Board findBoard = em.find(Board.class, boardId);

        return Optional.of(findBoard);
    }

    @Override
    public Optional<Member> findMemberByBoardId(Long boardId){

        Board findBoard = findBoardByBoardId(boardId).get();

        Member findMember = findBoard.getMember();

        return Optional.of(findMember);
    }


    @Override
    public Optional<Board> updateBoardByBoardId(Long boardId) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteBoardByBoardId(Long boardId) {

        if(findBoardByBoardId(boardId).get() != null){
            em.remove(em.find(Board.class, boardId));
            return true;
        }
        return false;
    }
}
