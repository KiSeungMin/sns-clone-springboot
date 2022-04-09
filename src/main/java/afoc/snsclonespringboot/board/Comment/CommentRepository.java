package afoc.snsclonespringboot.board.Comment;

import afoc.snsclonespringboot.board.like.Like;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    // 등록
    Optional<Comment> save(Comment comment);

    // 댓글 목록 조회
    List<Comment> findCommentListByBoardId(Long boardId);
}
