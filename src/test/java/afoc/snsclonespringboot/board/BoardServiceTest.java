package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.like.Like;
import afoc.snsclonespringboot.board.like.LikeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    LikeRepository likeRepository;

    @AfterEach
    public void afterEach() {
        boardRepository.clear();
        likeRepository.clear();
    }

    /*------------------------------------------------------*/
    // Boolean upload(Board board)

    @Test
    void uploadSingleTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);

        // successful upload
        assertThat(isSuccess1).isTrue();
    }

    @Test
    void uploadDoubleTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Board board2 = Board.builder()
                .memberId(2L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);
        Boolean isSuccess2 = boardService.upload(board2);

        // successful upload
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();
    }

    @Test
    void uploadOneMemberTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Board board2 = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);
        Boolean isSuccess2 = boardService.upload(board2);

        // successful upload
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();
    }

    /*------------------------------------------------------*/
    // Optional<Board> findBoardByBoardId(Long boardId)

    @Test
    void findBoardByBoardIdSingleTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();


        Boolean isSuccess1 = boardService.upload(board1);

        // successful upload
        assertThat(isSuccess1).isTrue();

        Optional<Board> foundBoard = boardService.findBoardByBoardId(board1.getBoardId());

        // Not null
        assertThat(foundBoard).isPresent();

        // Must same value
        assertThat(foundBoard.get()).isEqualTo(board1);
    }

    @Test
    void findBoardByBoardIdDoubleTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Board board2 = Board.builder()
                .memberId(2L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);
        Boolean isSuccess2 = boardService.upload(board2);

        // successful upload
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();

        Optional<Board> foundBoard1 = boardService.findBoardByBoardId(board1.getBoardId());
        Optional<Board> foundBoard2 = boardService.findBoardByBoardId(board2.getBoardId());

        // Not null
        assertThat(foundBoard1).isPresent();
        assertThat(foundBoard2).isPresent();

        // Must same value
        assertThat(foundBoard1.get()).isEqualTo(board1);
        assertThat(foundBoard2.get()).isEqualTo(board2);
    }

    /*------------------------------------------------------*/
    // List<Board> findBoardListByMemberId(Long memberId)

    @Test
    void findBoardListByMemberIdSingleTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);

        // successful upload
        assertThat(isSuccess1).isTrue();

        List<Board> foundBoardList1 = boardService.findBoardListByMemberId(board1.getMemberId());

        // # elements must be one
        assertThat(foundBoardList1).hasSize(1);

        // The element must be same
        assertThat(foundBoardList1.get(0)).isEqualTo(board1);
    }

    @Test
    void findBoardListByMemberIdMultipleTest() {
        int numMembers = 10;
        for (int i=1;i<=numMembers;i++){
            for (int j=0;j<i;j++) {
                Board board = Board.builder()
                        .memberId((long) i)
                        .build();
                Boolean isSuccess = boardService.upload(board);

                // successful upload
                assertThat(isSuccess).isTrue();
            }
        }

        for (int i=1;i<=numMembers;i++){
            List<Board> foundBoardList = boardService.findBoardListByMemberId((long) i);
            // # elements must be same by memberId
            assertThat(foundBoardList).hasSize(i);
        }
    }

    /*------------------------------------------------------*/
    // Boolean updateBoard(Board board)

    @Test
    void updateBoardSingleTest() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);

        // successful upload
        assertThat(isSuccess1).isTrue();

        // find member to be updated
        Optional<Board> foundBoard1ById = boardService.findBoardByBoardId(board1.getBoardId());
        assertThat(foundBoard1ById).isPresent(); // Not null

        // set new value
        Board foundMember1 = foundBoard1ById.get();
        Board updateBoard = Board.builder()
                .memberId(1L)
                .build();
        updateBoard.setBoardId(foundMember1.getBoardId());

        // update
        boolean isSuccess2 = boardService.updateBoard(updateBoard);
        assertThat(isSuccess2).isTrue(); // successful update

        // find updated board
        Optional<Board> foundBoardByBoardId = boardService.findBoardByBoardId(updateBoard.getBoardId());
        assertThat(foundBoardByBoardId).isPresent(); // Not null

        // check updated value
        assertThat(updateBoard).isEqualTo(foundBoardByBoardId.get());
    }

    @Test
    void updateBoardMemberIdCheck() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);

        // successful upload
        assertThat(isSuccess1).isTrue();

        // find member to be updated
        Optional<Board> foundBoard1ById = boardService.findBoardByBoardId(board1.getBoardId());
        assertThat(foundBoard1ById).isPresent(); // Not null

        // set new value
        Board foundMember1 = foundBoard1ById.get();
        Board updateBoard = Board.builder()
                .memberId(2L)
                .build();
        updateBoard.setBoardId(foundMember1.getBoardId());

        // update
        boolean isSuccess2 = boardService.updateBoard(updateBoard);
        assertThat(isSuccess2).isFalse(); // update failure

        // find updated board
        Optional<Board> foundBoardByBoardId = boardService.findBoardByBoardId(updateBoard.getBoardId());
        assertThat(foundBoardByBoardId).isPresent(); // Not null

        // check updated value
        assertThat(board1).isEqualTo(foundBoardByBoardId.get());
    }

    /*------------------------------------------------------*/
    // Boolean deleteBoardByBoardId(Long boardId);

    @Test
    void deleteBoardByBoardId() {
        Board board1 = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board1);

        // successful upload
        assertThat(isSuccess1).isTrue();

        // delete
        Boolean isSuccess2 = boardService.deleteBoardByBoardId(board1.getBoardId());
        assertThat(isSuccess2).isTrue();

        // check
        List<Board> memberBoardList = boardService.findBoardListByMemberId(board1.getMemberId());
        Optional<Board> boardByBoardId = boardService.findBoardByBoardId(board1.getBoardId());
        assertThat(memberBoardList).hasSize(0);
        assertThat(boardByBoardId).isEmpty();
    }

    /*------------------------------------------------------*/
    // Boolean likeBoard(Long boardId, Long memberId);

    @Test
    void likeBoardSingleTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        Boolean isSuccess2 = boardService.likeBoard(board.getBoardId(), 2L);

        // successful like
        assertThat(isSuccess2).isTrue();
    }

    @Test
    void likeBoardManyTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        for(int i=0;i<30;i++){
            Boolean isSuccess = boardService.likeBoard(board.getBoardId(), (long) (i + 2));
            assertThat(isSuccess).isTrue();  // successful like
        }
    }

    @Test
    void likeBoardMultipleTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        for(int i=0;i<30;i++) {
            Boolean isSuccess = boardService.likeBoard(board.getBoardId(), 2L);
            if (i==0){
                assertThat(isSuccess).isTrue();  // successful like
            } else {
                assertThat(isSuccess).isFalse();  // must fail, only like once
            }
        }
    }

    /*------------------------------------------------------*/
    // Boolean likeCancel(Long boardId, Long memberId);

    @Test
    void likeCancelSingleTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Long likeMemberId = 2L;

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        Boolean isSuccess2 = boardService.likeBoard(board.getBoardId(), likeMemberId);

        // successful like
        assertThat(isSuccess2).isTrue();

        Boolean isSuccess3 = boardService.likeCancel(board.getBoardId(), likeMemberId);

        // successful like cancel
        assertThat(isSuccess3).isTrue();

    }

    @Test
    void likeCancelManyTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        for(int i=0;i<30;i++){
            Boolean isSuccess = boardService.likeBoard(board.getBoardId(), (long) (i + 2));
            assertThat(isSuccess).isTrue();  // successful like
        }
        for(int i=0;i<30;i++){
            Boolean isSuccess = boardService.likeCancel(board.getBoardId(), (long) (i + 2));
            assertThat(isSuccess).isTrue();  // successful like cancel
        }
    }

    @Test
    void likeCancelMultipleTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Long likeMemberId = 2L;

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        Boolean isSuccess2 = boardService.likeBoard(board.getBoardId(), likeMemberId);
        assertThat(isSuccess2).isTrue();  // successful like

        for(int i=0;i<30;i++) {
            Boolean isSuccess3 = boardService.likeCancel(board.getBoardId(), likeMemberId);
            if (i==0){
                assertThat(isSuccess3).isTrue();  // successful like cancel
            } else {
                assertThat(isSuccess3).isFalse();  // must fail, only like cancel once
            }
        }
    }

    /*------------------------------------------------------*/
    // List<Long> findLikeMemberList(Long boardId);

    @Test
    void findLikeMemberListZeroTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        List<Long> likeMemberList = boardService.findLikeMemberList(board.getBoardId());
        assertThat(likeMemberList).hasSize(0);
    }

    @Test
    void findLikeMemberListTest() {
        Board board = Board.builder()
                .memberId(1L)
                .build();

        Boolean isSuccess1 = boardService.upload(board);

        // successful upload
        assertThat(isSuccess1).isTrue();

        for(int i=0;i<30;i++){
            Boolean isSuccess = boardService.likeBoard(board.getBoardId(), (long) (i + 2));
            assertThat(isSuccess).isTrue();  // successful like
        }

        List<Long> likeMemberList1 = boardService.findLikeMemberList(board.getBoardId());
        assertThat(likeMemberList1).hasSize(30);

        for(int i=0;i<30;i++){
            Boolean isSuccess = boardService.likeCancel(board.getBoardId(), (long) (i + 2));
            assertThat(isSuccess).isTrue();  // successful like cancel
        }

        List<Long> likeMemberList2 = boardService.findLikeMemberList(board.getBoardId());
        assertThat(likeMemberList2).hasSize(0);
    }
}