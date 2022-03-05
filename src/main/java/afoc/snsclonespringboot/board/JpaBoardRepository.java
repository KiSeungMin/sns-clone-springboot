package afoc.snsclonespringboot.board;

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
    public Optional<Board> findByBoardId(Long boardId) {

        Board findBoard = em.find(Board.class, boardId);

        return Optional.of(findBoard);
    }

    @Override
    public List<Board> findByMemberId(Long memberId) {
        // 구현해야 함
        return null;
    }

    @Override
    public Optional<Board> updateByBoardId(Long boardId) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteByBoardId(Long boardId) {
        return null;
    }
}
