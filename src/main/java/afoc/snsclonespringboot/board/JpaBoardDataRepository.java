package afoc.snsclonespringboot.board;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaBoardDataRepository implements BoardDataRepository{

    @PersistenceContext
    private EntityManager em;

    public JpaBoardDataRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Optional<BoardData> save(BoardData boardData) {
        em.persist(boardData);

        return Optional.of(boardData);
    }

    @Override
    public List<BoardData> findByBoardId(Long boardId) {
        return em.createQuery("select b from BoardData b where b.boardId = :boardId", BoardData.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Boolean deleteByBoardId(Long boarId) {
        List<BoardData> boardDataList = findByBoardId(boarId);
        if(boardDataList.isEmpty())
            return false;

        for(BoardData boardData : boardDataList){
            em.remove(boardData.getId());
        }
        return true;
    }
}
