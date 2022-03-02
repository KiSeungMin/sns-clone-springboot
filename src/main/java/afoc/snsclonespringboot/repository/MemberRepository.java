package afoc.snsclonespringboot.repository;

import afoc.snsclonespringboot.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    // 등록
    Member save(Member member);

    // Member id 이용해 유저 조회
    Optional<Member> findByMemberId(Long memberId);

    // Member id 이용해 수정
    Optional<Member> updateByMemberId(Long memberId);

    // Member id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteByMemberId(Long memberId);
}
