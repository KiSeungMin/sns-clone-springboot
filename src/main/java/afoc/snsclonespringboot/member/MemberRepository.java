package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {

    // Member CRUD

    // 등록
    Member save(Member member);

    // Member id 이용해 유저 조회
    Optional<Member> findMemberByMemberId(Long id);

    // Member username 이용해 유저 조회
    Optional<Member> findMemberByMemberEmail(String email);

    // Member id 이용해 게시판 리스트 조회
    List<Board> findBoardListByMemberId(Long memberId);

    // Member id 이용해 수정
    Boolean updateMember(Member member);

    // Member id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteMemberByMemberId(Long memberId);

    /*------------------------------------------------------*/

    // TODO
    // Follow


    /*------------------------------------------------------*/
    // all clear for test
    void clear();
}
