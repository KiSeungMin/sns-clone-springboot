package afoc.snsclonespringboot.member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    /*
    - 유저 등록/조회/수정/삭제
    - 팔로우 하기/끊기
    - 팔로워 목록 조회
    - 팔로이 목록 조회
     */



    // Member CRUD
    Boolean join(Member member);
    Optional<Member> findMemberById(Long id);
    Optional<Member> findMemberByEmail(String email);
    Boolean updateMember(Member member);
    Boolean deleteMemberById(Long id);

    // Follow
    Boolean follow(Long followerId, Long followeeId);
    Boolean unfollow(Long followerId, Long followeeId);
    List<Long> findFollowers(Long followeeId);
    List<Long> findFollowees(Long followerId);
    public Boolean followIsPresent(Long followerId, Long followeeId);
}
