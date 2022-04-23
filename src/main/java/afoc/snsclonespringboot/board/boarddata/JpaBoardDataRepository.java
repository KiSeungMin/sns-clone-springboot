package afoc.snsclonespringboot.board.boarddata;

import afoc.snsclonespringboot.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaBoardDataRepository implements BoardDataRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<BoardData> save(BoardData boardData) {
        em.persist(boardData);

        return Optional.of(boardData);
    }

    @Override
    public List<BoardData> findByBoard(Board board) {
        return em.createQuery("select b from BoardData b where b.board = :board", BoardData.class)
                .setParameter("board", board)
                .getResultList();
    }

    @Override
    public Boolean deleteByBoard(Board board) {
        List<BoardData> boardDataList = findByBoard(board);
        if(boardDataList.isEmpty())
            return false;

        for(BoardData boardData : boardDataList){
            em.remove(boardData.getId());
        }
        return true;
    }
}
