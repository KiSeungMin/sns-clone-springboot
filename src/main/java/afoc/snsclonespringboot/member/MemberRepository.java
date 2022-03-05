package afoc.snsclonespringboot.member;

import java.util.Optional;


public interface MemberRepository {

    // 등록
    Member save(Member member);

    // Member id 이용해 유저 조회
    Optional<Member> findByMemberId(Long id);

    // Member username 이용해 유저 조회
    Optional<Member> findByMemberEmail(String email);

    // Member id 이용해 수정
    Optional<Member> updateByMemberId(Long memberId);

    // Member id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteByMemberId(Long memberId);
}
