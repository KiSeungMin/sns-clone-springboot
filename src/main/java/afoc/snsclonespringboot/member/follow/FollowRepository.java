package afoc.snsclonespringboot.member.follow;

import java.util.List;

public interface FollowRepository {
    // 등록
    Follow save(Follow follow);

    // Follwer id 이용해 followees 조회
    List<Long> findFolloweesByFollowerId(Long followerId);

    // Followee id 이용해 followers 조회
    List<Long> findFollowersByFolloweeId(Long followeeId);

    // 수정 X

    // 언팔
    // return 값 성공/실패
    Boolean deleteFollowee(Long followerId, Long followeeId);
}
