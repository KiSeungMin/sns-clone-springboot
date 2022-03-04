package afoc.snsclonespringboot.repository;

import afoc.snsclonespringboot.domain.Follow;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FollowRepositoryImpl implements FollowRepository {

    @PersistenceContext
    private EntityManager em;

    public FollowRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Follow save(Follow follow) {

        em.persist(follow);

        return follow;
    }

    @Override
    public List<Long> findFolloweesByFollowerId(Long followerId) {
        // 구현해야 함
        return null;
    }

    @Override
    public List<Long> findFollowersByFolloweeId(Long followeeId) {
        // 구현해야 함
        return null;
    }

    @Override
    public Boolean deleteFollowee(Long followerId, Long followeeId) {
        // 구현해야 함
        return null;
    }
}