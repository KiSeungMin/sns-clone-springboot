package afoc.snsclonespringboot.member.follow;

import afoc.snsclonespringboot.board.like.Like;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryFollowRepository implements FollowRepository {
    private static final Map<Long, Follow> store = new HashMap<>();
    private static long sequence = 0L;

    /*------------------------------------------------------*/
    // Follow CRD

    @Override
    public Optional<Follow> save(Follow follow) {
        follow.setId(++sequence);
        store.put(follow.getId(), follow);
        return Optional.ofNullable(follow);
    }

    @Override
    public Optional<Follow> findFollow(Long followerId, Long followeeId) {
        return store.values().stream()
                    .filter(follow -> follow.getFollowerId().equals(followerId))
                    .filter(follow -> follow.getFolloweeId().equals(followeeId))
                    .findAny();
    }

    @Override
    public List<Long> findFolloweesByFollowerId(Long followerId) {
        return store.values().stream()
                    .filter(follow -> follow.getFollowerId().equals(followerId))
                    .map(Follow::getFolloweeId)
                    .collect(Collectors.toList());
    }

    @Override
    public List<Long> findFollowersByFolloweeId(Long followeeId) {
        return store.values().stream()
                    .filter(follow -> follow.getFolloweeId().equals(followeeId))
                    .map(Follow::getFollowerId)
                    .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteFollow(Long followerId, Long followeeId) {
        Optional<Follow> findFollow = findFollow(followerId, followeeId);
        if(findFollow.isPresent()) {
            Follow ret = store.remove(findFollow.get().getId());
            return ret != null;
        } else {
            return false;
        }
    }

    @Override
    public void clear(){
        store.clear();
        sequence = 0L;
    }
}
