package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.like.Like;
import afoc.snsclonespringboot.member.JpaMemberRepository;
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
    public List<Board> findBoardListByMemberId(Long memberId){

        List<Board> findBoard= em.createQuery("select b from Board b where b.memberId = :memberId", Board.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return findBoard;
    }

    @Override
    public Optional<Board> updateBoardByBoardId(Long boardId) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteBoardByBoardId(Long boardId) {

        Optional<Board> findBoard = findBoardByBoardId(boardId);

        if(findBoard.isPresent()){
            em.remove(findBoard.get());
            return true;
        }

        return false;
    }

}
