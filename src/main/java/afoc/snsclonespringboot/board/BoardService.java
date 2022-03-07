package afoc.snsclonespringboot.board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    /*
    - 게시물 등록
    - 게시물 id로 조회
    - 게시물 id로 수정
    - 게시물 id로 석제
    - 게시물 id로 좋아요 목록 조회
    - TODO
        - 댓글 기능
     */

    // TODO
    Boolean upload(Board board);
    Optional<Board> findBoardByBoardId(Long boardId);
    List<Board> findBoardListByMemberId(Long memberId);
    Boolean updateBoard(Board board);
    Boolean deleteBoardByBoardId(Long boardId);
}
