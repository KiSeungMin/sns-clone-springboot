package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.Comment.Comment;
import afoc.snsclonespringboot.board.boarddata.BoardData;
import afoc.snsclonespringboot.member.Member;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    /*
    - 게시물 등록/조회/수정/삭제
    - 게시물 좋아요/좋아요 취소/좋아요 한 사람 목록 조회
    - TODO
        - 댓글 기능
     */

    // basic functions
    Optional<Board> upload(Board board);
    Optional<Board> findBoardByBoardId(Long boardId);
    List<Board> findBoardListByMemberId(Long memberId);
    Boolean updateBoard(Board board);
    Boolean deleteBoardByBoardId(Long boardId);

    // like functions
    Boolean likeBoard(Long boardId, Long memberId);
    Boolean boardLike(Long boardId, Long memberId);
    Boolean likeIsPresent(Long boardId, Long memberId);
    Boolean likeCancel(Long boardId, Long memberId);
    List<Long> findLikeMemberList(Long boardId);

    Boolean addComment(Long boardId, Member member, String content);
    List<Comment> getCommentList(Long boardId);

    // BoardData functions
    Optional<BoardData> uploadBoardData(BoardData boardData);
}
