package afoc.snsclonespringboot.board.boarddata;

import afoc.snsclonespringboot.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDataRepository {
    // 등록
    Optional<BoardData> save(BoardData boardData);

    // 찾기
    List<BoardData> findByBoard(Board board);

    // 삭제
    Boolean deleteByBoard(Board board);
}
