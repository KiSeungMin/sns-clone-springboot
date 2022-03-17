package afoc.snsclonespringboot.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    //@Autowired
    private final BoardRepository boardRepository;

    @Override
    public Boolean upload(Board board) {
        boardRepository.save(board);
        return true;
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
        return null;
    }

    @Override
    public Boolean deleteBoardByBoardId(Long boardId) {
        return boardRepository.deleteBoardByBoardId(boardId);
    }
}
