package afoc.snsclonespringboot.member.follow;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaFollowRepository implements FollowRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaFollowRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Follow save(Follow follow) {

        em.persist(follow);

        return follow;
    }

    public Follow findFollow(Long followerId, Long followeeId){

        Follow findFollow = em.createQuery("select f from Follow f where f.followerId = :followerId and " +
                        "f.followeeId = :followeeId", Follow.class)
                .setParameter("followerId", followerId)
                .setParameter("followeeId", followeeId)
                .getSingleResult();

        return findFollow;

    }

    @Override
    public List<Long> findFolloweesByFollowerId(Long followerId) {

        List<Long> findFollowees = em.createQuery("select F.followeeId from Follow F where F.followerId = '"
        + followerId + "'").getResultList();

        return findFollowees;
    }

    @Override
    public List<Long> findFollowersByFolloweeId(Long followeeId) {

        List<Long> findFollowers = em.createQuery("select F.followerId from Follow F where F.followeeId = '"
        + followeeId + "'").getResultList();

        return findFollowers;
    }

    @Override
    public Boolean deleteFollowee(Long followerId, Long followeeId) {

        Follow findFollow = findFollow(followerId, followeeId);

        if(findFollow != null){

            em.remove(findFollow);
            return true;
        }

        return false;
    }
}
