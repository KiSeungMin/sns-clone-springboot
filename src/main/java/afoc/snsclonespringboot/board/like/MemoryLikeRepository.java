package afoc.snsclonespringboot.board.like;

import afoc.snsclonespringboot.member.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryLikeRepository implements LikeRepository {
    private static final Map<Long, Like> store = new HashMap<>();
    private static long sequence = 0L;

    /*------------------------------------------------------*/
    // Like CRUD

    @Override
    public Optional<Like> save(Like like) {
        like.setId(++sequence);
        store.put(like.getId(), like);
        return Optional.ofNullable(like);
    }

    @Override
    public List<Long> findLikeMemberListByBoardId(Long boardId) {
        return store.values().stream()
                .filter(like -> like.getBoardId().equals(boardId))
                .map(Like::getMemberId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Like> findLikeByBoardIdAndMemberId(Long boardId, Long memberId) {
        return store.values().stream()
                    .filter(like -> like.getBoardId().equals(boardId)
                            && like.getMemberId().equals(memberId))
                    .findAny();
    }

    @Override
    public Boolean deleteLike(Long boardId, Long memberId) {
        Optional<Like> findLike = findLikeByBoardIdAndMemberId(boardId, memberId);
        if(findLike.isEmpty()) {
            return false;
        }
        else {
            Like ret = store.remove(findLike.get().getId());
            return ret != null;
        }
    }

    /*------------------------------------------------------*/
    // Extras

    @Override
    public void clear(){
        store.clear();
        sequence = 0L;
    }
}
