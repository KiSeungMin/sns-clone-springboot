package afoc.snsclonespringboot.board.like;

import java.util.List;

public interface JpaLikeRepository {
    // 등록
    Like save(Like like);

    // 좋아요 목록 조회
    List<Like> findLikeListByBoardId(Long boardId);

    // 수정 X

    // 좋아요 취소

    // return 값 성공/실패
    Boolean deleteLike(Long boardId, Long memberId);
}