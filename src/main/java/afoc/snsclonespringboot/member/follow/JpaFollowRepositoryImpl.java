package afoc.snsclonespringboot.member.follow;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaFollowRepositoryImpl implements JpaFollowRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaFollowRepositoryImpl(EntityManager em) {
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
