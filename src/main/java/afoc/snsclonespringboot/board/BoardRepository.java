package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.member.Member;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    // 등록
    Board save(Board board);

    // Board id 이용해 게시글 조회
    Optional<Board> findBoardByBoardId(Long boardId);

    // Member Id를 이용해 Board List 조회
    List<Board> findBoardListByMember(Member member);

    // Board Id 이용해 수정
    // return 값 성공/실패
    Boolean updateBoard(Board board);

    // Board Id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteBoardByBoardId(Long boardId);

    /*------------------------------------------------------*/
    // all clear for test
    void clear();
}
