package afoc.snsclonespringboot.board.like;

import afoc.snsclonespringboot.board.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
public class JpaLikeRepository implements LikeRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaLikeRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Like save(Like like) {
        em.persist(like);

        return like;
    }

    public Like findLikeByLikeId(Long likeId){

        Like findLike = em.find(Like.class, likeId);

        return findLike;
    }

    @Override
    public List<Long> findLikeListByBoardId(Long boardId) {

        List<Long> likeList = em.createQuery("select L.userId from Like L where L.boardId = :boardId")
                .setParameter("boardId", boardId)
                .getResultList();

        return likeList;

    }

    @Override
    public Boolean deleteLike(Long boardId, Long userId){

        Like findLike = em.createQuery("select L from Like L where L.boardId = :boardId and L.userId = :userId"
        , Like.class)
                .setParameter("boardId", boardId)
                .setParameter("userId", userId)
                .getSingleResult();

        if(findLike != null) {
            em.remove(findLike);
            return true;
        }

        return false;
    }
}
