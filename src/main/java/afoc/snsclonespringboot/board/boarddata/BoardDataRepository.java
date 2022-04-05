package afoc.snsclonespringboot.board.boarddata;

import afoc.snsclonespringboot.board.boarddata.BoardData;

import java.util.List;
import java.util.Optional;

public interface BoardDataRepository {
    // 등록
    Optional<BoardData> save(BoardData boardData);

    // 찾기
    List<BoardData> findByBoardId(Long boardId);

    // 삭제
    Boolean deleteByBoardId(Long boardId);
}
