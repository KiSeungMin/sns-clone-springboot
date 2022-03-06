package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.member.Member;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    // 등록
    Board save(Board board);

    // Board id 이용해 게시글 조회
    Optional<Board> findBoardByBoardId(Long boardId);

    List<Board> findBoardListByMemberId(Long memberId);

    // Board Id 이용해 수정
    Optional<Board> updateBoardByBoardId(Long boardId);

    // Board Id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteBoardByBoardId(Long boardId);
}
