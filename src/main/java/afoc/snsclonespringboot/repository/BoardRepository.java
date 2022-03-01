package afoc.snsclonespringboot.repository;

import afoc.snsclonespringboot.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    // 등록
    Board save(Board board);

    // Board id 이용해 게시글 조회
    Optional<Board> findByBoardId(Long boardId);

    // User id 이용해 모든 게시글 조회
    List<Board> findByUserId(Long userId);

    // Board Id 이용해 수정
    Optional<Board> updateByBoardId(Long boardId);

    // Board Id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteByBoardId(Long boardId);
}
