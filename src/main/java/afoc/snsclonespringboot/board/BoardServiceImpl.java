package afoc.snsclonespringboot.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public Boolean upload(Board board) {
        try {
            boardRepository.save(board);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public Optional<Board> findBoardByBoardId(Long boardId) {
        return boardRepository.findBoardByBoardId(boardId);
    }

    @Override
    public List<Board> findBoardListByMemberId(Long memberId) {
        return boardRepository.findBoardListByMemberId(memberId);
    }

    @Override
    public Boolean updateBoard(Board board) {
        Optional<Board> foundBoard = findBoardByBoardId(board.getBoardId());
        if (foundBoard.isEmpty()) {
            return false;
        } else {
            Long memberId1 = board.getMemberId();
            Long memberId2 = foundBoard.get().getMemberId();
            if (!Objects.equals(memberId1, memberId2)){
                return false;
            } else {
                boardRepository.updateBoard(board);
                return true;
            }
        }
    }

    @Override
    public Boolean deleteBoardByBoardId(Long boardId) {
        return boardRepository.deleteBoardByBoardId(boardId);
    }
}
