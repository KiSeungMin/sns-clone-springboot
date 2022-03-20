package afoc.snsclonespringboot.member.follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository {
    // 등록
    Optional<Follow> save(Follow follow);

    // follower id 와 followee id 이용해 조회
    Optional<Follow> findFollow(Long followerId, Long followeeId);

    // Follwer id 이용해 followees 조회
    List<Long> findFolloweesByFollowerId(Long followerId);

    // Followee id 이용해 followers 조회
    List<Long> findFollowersByFolloweeId(Long followeeId);

    // 수정 X

    // 언팔
    // return 값 성공/실패
    Boolean deleteFollow(Long followerId, Long followeeId);

    void clear();
}
