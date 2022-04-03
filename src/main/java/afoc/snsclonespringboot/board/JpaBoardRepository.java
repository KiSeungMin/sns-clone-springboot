package afoc.snsclonespringboot.board;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaBoardRepository implements BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaBoardRepository(EntityManager em){
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

        return Optional.ofNullable(findBoard);
    }

    @Override
    public List<Board> findBoardListByMemberId(Long memberId){
        return em.createQuery("select b from Board b where b.memberId = :memberId", Board.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Boolean updateBoard(Board board) {
        return false;
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

    public Boolean deleteBoardList(List<Board> board){

        if(!board.isEmpty()){
            for(Board b : board){
                deleteBoardByBoardId(b.getBoardId());
            }
            return true;
        }
        return false;
    }

    @Override
    public void clear(){
    }
}
