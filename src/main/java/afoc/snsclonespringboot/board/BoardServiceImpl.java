package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.Comment.Comment;
import afoc.snsclonespringboot.board.Comment.CommentRepository;
import afoc.snsclonespringboot.board.like.Like;
import afoc.snsclonespringboot.board.like.LikeRepository;
import afoc.snsclonespringboot.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    /*------------------------------------------------------*/
    // Basic Board Functions

    @Override
    public Boolean upload(Board board) {

        try {
            boardRepository.save(board);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
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

    /*------------------------------------------------------*/
    // Like Functions

    @Override
    public Boolean likeBoard(Long boardId, Long memberId) {
        Optional<Like> findLike = likeRepository.findLikeByBoardIdAndMemberId(boardId, memberId);
        if (findLike.isEmpty()){
            Like newLike = Like.builder()
                    .boardId(boardId)
                    .memberId(memberId)
                    .build();
            Optional<Like> res = likeRepository.save(newLike);
            return res.isPresent();
        } else {
            return false;
        }
    }

    public Boolean likeIsPresent(Long boardId, Long memberId){
        return likeRepository.findLikeByBoardIdAndMemberId(boardId, memberId).isPresent();
    }

    @Override
    public Boolean likeCancel(Long boardId, Long memberId) {
        return likeRepository.deleteLike(boardId, memberId);
    }


    public Boolean boardLike(Long boardId, Long memberId){

        Optional<Like> findLike = likeRepository.findLikeByBoardIdAndMemberId(boardId, memberId);
        if (findLike.isEmpty()){
            Like newLike = Like.builder()
                    .boardId(boardId)
                    .memberId(memberId)
                    .build();
            Optional<Like> res = likeRepository.save(newLike);
            return true;
        } else{
            likeRepository.deleteLike(boardId, memberId);
            return false;
        }
    }

    @Override
    public List<Long> findLikeMemberList(Long boardId) {
        return likeRepository.findLikeMemberListByBoardId(boardId);
    }

    public Boolean addComment(Long boardId, Member member, String content){

        Comment comment = Comment.builder()
                .boardId(boardId)
                .member(member)
                .content(content)
                .date(new Date())
                .build();

        Optional<Comment> res = commentRepository.save(comment);

        return res.isPresent();
    }

    public List<Comment> getCommentList(Long boardId){
        return commentRepository.findCommentListByBoardId(boardId);
    }
}
