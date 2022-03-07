package afoc.snsclonespringboot.board;

import java.util.List;
import java.util.Optional;

public class BoardServiceImpl implements BoardService {
    @Override
    public Boolean upload(Board board) {
        return null;
    }

    @Override
    public Optional<Board> findBoardByBoardId(Long boardId) {
        return Optional.empty();
    }

    @Override
    public List<Board> findBoardListByMemberId(Long memberId) {
        return null;
    }

    @Override
    public Boolean updateBoard(Board board) {
        return null;
    }

    @Override
    public Boolean deleteBoardByBoardId(Long boardId) {
        return null;
    }
}
