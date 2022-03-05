package afoc.snsclonespringboot.board.like;

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

    @Override
    public List<Like> findLikeListByBoardId(Long boardId) {

        // 구현해야함
        List<Like> likeList=  Arrays.asList();

        return likeList;
    }


    @Override
    public Boolean deleteLike(Long boardId, Long memberId) {

        // 구현해야함
        return false;
    }
}
