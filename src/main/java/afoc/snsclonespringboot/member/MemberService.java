package afoc.snsclonespringboot.member;

import java.util.Optional;

public interface MemberService {
    /*
    - 유저 등록/조회/수정/삭제
    - 팔로우 하기
    - 팔로우 끊기
    - 팔로워 목록 조회
    - 팔로이 목록 조회
     */
    boolean join(Member member);
    Optional<Member> findMemberById(Long memberId);
    Optional<Member> findByEmail(String email);
}
