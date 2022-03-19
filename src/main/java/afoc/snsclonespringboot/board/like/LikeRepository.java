package afoc.snsclonespringboot.board.like;

import afoc.snsclonespringboot.member.Member;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {
    // 등록
    Optional<Like> save(Like like);

    // 좋아요 목록 조회
    List<Long> findLikeMemberListByBoardId(Long boardId);

    // 특정 좋아요 조회
    Optional<Like> findLikeByBoardIdAndMemberId(Long boardId, Long memberId);

    // 수정 X

    // 좋아요 취소
    // return 값 성공/실패
    Boolean deleteLike(Long boardId, Long memberId);

    // for test
    void clear();
}
