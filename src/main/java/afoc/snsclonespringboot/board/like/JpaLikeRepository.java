package afoc.snsclonespringboot.board.like;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaLikeRepository implements LikeRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaLikeRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Optional<Like> save(Like like) {
        em.persist(like);

        return Optional.ofNullable(like);
    }

    @Override
    public List<Long> findLikeMemberListByBoardId(Long boardId) {

        List<Long> likeList = em.createQuery("select L.memberId from Like L where L.boardId = :boardId")
                .setParameter("boardId", boardId)
                .getResultList();

        return likeList;
    }

    @Override
    public Optional<Like> findLikeByBoardIdAndMemberId(Long boardId, Long memberId) {
        List<Like> findLikes = em.createQuery("select L from Like L where L.boardId = :boardId and L.memberId = :memberId"
                        , Like.class)
                .setParameter("boardId", boardId)
                .setParameter("memberId", memberId)
                .getResultList();
        return  findLikes.stream().findAny();
    }

    @Override
    public Boolean deleteLike(Long boardId, Long memberId){
        Optional<Like> findLike = findLikeByBoardIdAndMemberId(boardId, memberId);
        if(findLike.isPresent()) {
            em.remove(findLike.get());
            return true;
        }
        return false;
    }

    @Override
    public void clear() {

    }
}
