package afoc.snsclonespringboot.board;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryBoardRepository implements BoardRepository{
    private static final Map<Long, Board> store = new HashMap<>();
    private static long sequence = 0L;

    /*------------------------------------------------------*/
    // Board CRUD

    @Override
    public Board save(Board board) {
        board.setBoardId(++sequence);
        store.put(board.getBoardId(), board);
        return board;
    }

    @Override
    public Optional<Board> findBoardByBoardId(Long boardId) {
        return Optional.ofNullable(store.get(boardId));
    }

    @Override
    public List<Board> findBoardListByMemberId(Long memberId) {
        return store.values().stream()
                    .filter(board -> board.getMemberId().equals(memberId))
                    .collect(Collectors.toList());
    }

    @Override
    public Boolean updateBoard(Board board) {
        Board returnValue = store.get(board.getBoardId());
        if (returnValue != null) {
            store.put(board.getBoardId(), board);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteBoardByBoardId(Long boardId) {
        Board returnValue = store.remove(boardId);
        return returnValue != null;
    }

    /*------------------------------------------------------*/
    // Like

    /*------------------------------------------------------*/
    // Extras

    @Override
    public void clear(){
        store.clear();
        sequence = 0L;
    }
}
