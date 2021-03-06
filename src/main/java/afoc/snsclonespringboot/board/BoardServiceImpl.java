package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.Comment.Comment;
import afoc.snsclonespringboot.board.Comment.CommentRepository;
import afoc.snsclonespringboot.board.boarddata.BoardData;
import afoc.snsclonespringboot.board.boarddata.BoardDataRepository;
import afoc.snsclonespringboot.board.like.Like;
import afoc.snsclonespringboot.board.like.LikeRepository;
import afoc.snsclonespringboot.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final BoardDataRepository boardDataRepository;

    /*------------------------------------------------------*/
    // Basic Board Functions

    @Override
    public Optional<Board> upload(Board board) {

        try {
            boardRepository.save(board);
            return Optional.of(board);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Board> findBoardByBoardId(Long boardId) {
        return boardRepository.findBoardByBoardId(boardId);
    }

    @Override
    public List<Board> findBoardListByMember(Member member) {
        return boardRepository.findBoardListByMember(member);
    }

    @Override
    public List<Board> findBoardListByMemberId(Long memberId) {
        return boardRepository.findBoardListByMemberId(memberId);
    }

    @Override
    public List<Board> findFolloweeBoardListByFolloweeList(List<Long> followeeList){

        List<Board> followeeBoardList = new ArrayList<>();

        for(Long L : followeeList){

            List<Board> boardList = boardRepository.findBoardListByMemberId(L);

            for(Board board : boardList){
                followeeBoardList.add(board);
            }
        }

        Collections.sort(followeeBoardList, Collections.reverseOrder(Board::compareTo));

        return followeeBoardList;
    }

    /*
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
    */

    @Override
    public Boolean updateBoard(Board board){
        return true;
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

    @Override
    public Boolean likeIsPresent(Long boardId, Long memberId){
        return likeRepository.findLikeByBoardIdAndMemberId(boardId, memberId).isPresent();
    }

    @Override
    public Boolean likeCancel(Long boardId, Long memberId) {
        return likeRepository.deleteLike(boardId, memberId);
    }

    @Override
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

    public List<Comment> getCommentList(Long boardId) {
        return commentRepository.findCommentListByBoardId(boardId);
    }
    /*------------------------------------------------------*/
    // BoardData Functions
    @Override
    public Optional<BoardData> uploadBoardData(BoardData boardData) {

        try {
            boardDataRepository.save(boardData);
            return Optional.of(boardData);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Long> findBoardDataInfoIdByBoard(Board board) {
        List<Long> retList = new ArrayList<>();
        List<BoardData> boardDataList = boardDataRepository.findByBoard(board);
        for(BoardData boardData : boardDataList){
            retList.add(boardData.getDataInfo().getId());
        }
        return retList;
    }
}
