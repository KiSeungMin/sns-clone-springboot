package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.Comment.Comment;
import afoc.snsclonespringboot.board.Comment.CommentForm;
import afoc.snsclonespringboot.board.like.LikeForm;
import afoc.snsclonespringboot.member.FollowForm;
import afoc.snsclonespringboot.board.boarddata.BoardData;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final DataService dataService;

    @GetMapping(value="/board/new")
    public String uploadBoard(Model model){

        try{
            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            model.addAttribute("authMember", authenticationMember.get());
            model.addAttribute("boardForm", new BoardForm());
            return "createBoard";
        } catch(Exception e){
            return "error/500";
        }

    }

    @PostMapping(value="/board/new")
    public String uploadBoard(@ModelAttribute BoardForm boardForm, Model model){

        try{
            Optional<Member> authenticationMember = memberService.getAuthenticationMember();
            if (authenticationMember.isEmpty())
                throw new IllegalStateException();

            //String username = authenticationMember.get().getUsername();
            // Long memberId = authenticationMember.get().getId();

            List<BoardData> boardDataList = new ArrayList<>();

            Board board = Board.builder()
                    .member(authenticationMember.get())
                    //.memberId(memberId)
                    //.username(username)
                    .textData(boardForm.getTextData())
                    .date(new Date())
                    .boardDataList(boardDataList)
                    .build();

            Optional<Board> optionalBoard = boardService.upload(board);

            if(optionalBoard.isEmpty())
                throw new IllegalStateException();

            Long boardId = optionalBoard.get().getBoardId();

            for (MultipartFile multipartFile : boardForm.getImageFiles()) {
                if (!multipartFile.isEmpty()){
                    Optional<DataInfo> dataInfo = dataService.save(multipartFile, DataType.Image);
                    if(dataInfo.isEmpty())
                        throw new IllegalStateException();

                    //Long dataInfoId = dataInfo.get().getId();

                    BoardData boardData = BoardData.builder()
                            .board(board)
                            //.dataInfoId(dataInfoId)
                            .dataInfo(dataInfo.get())
                            .build();

                    board.getBoardDataList().add(boardData);

                    boardService.uploadBoardData(boardData);
                }
            }
            return "redirect:/main";
        } catch(IllegalStateException e){
            return "error/500";
        }
    }

//    @GetMapping(value="/board/new")
//    public String createBoard(Model model){
//
//        model.addAttribute("member", memberService.getAuthenticationMember().get());
//        model.addAttribute("boardForm", new BoardForm());
//
//        return "createBoard";
//    }
////
//    @PostMapping(value="/board/new")
//    public RedirectView createBoard(BoardForm boardForm, Model model){
//
//        Optional<Member> authenticationMember = memberService.getAuthenticationMember();
//        if (authenticationMember.isEmpty())
//            throw new IllegalStateException();
//        Member member = authenticationMember.get();
//
//        Board board = Board.builder()
//                .memberId(member.getId())
//                .username(member.getUsername())
//                .textData(boardForm.getTextData())
//                .date(new Date())
//                .build();
//
//        boardService.upload(board);
//
//        return new RedirectView("/main");
//    }

    @GetMapping(value="/board/{boardId}/get")
    public String visitBoardForm(@PathVariable("boardId") Long boardId, Model model){

        try{
            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            Board board = boardService.findBoardByBoardId(boardId).get();
            //Optional<Member> member = memberService.findMemberById(board.getMemberId());
            Member member = board.getMember();

            BoardContentForm boardContentForm = new BoardContentForm();

            List<Comment> commentList = boardService.getCommentList(boardId);

            boardContentForm.setBoard(board);

            //boardContentForm.setFollowIsPresent(memberService.followIsPresent(headerForm.getMemberId(), board.getMemberId()));
            //boardContentForm.setLikeIsPresent(boardService.likeIsPresent(board.getMemberId(), headerForm.getMemberId()));

            boardContentForm.setFollowIsPresent(memberService.followIsPresent(authenticationMember.get().getId(), member.getId()));
            boardContentForm.setLikeIsPresent(boardService.likeIsPresent(member.getId(), authenticationMember.get().getId()));

            //Optional<DataInfo> dataInfo = dataService.load(member.get().getImageDataInfoId());

            Optional<DataInfo> dataInfo = dataService.load(member.getImageDataInfoId());

            if(dataInfo.isEmpty()){
                throw new Exception();
            }
            String profileImagePath = dataInfo.get().getSaveDataPath();

            model.addAttribute("boardContentForm", boardContentForm);
            model.addAttribute("authMember", authenticationMember.get());
            model.addAttribute("commentList", commentList);
            model.addAttribute("likeForm", new LikeForm());
            model.addAttribute("commentForm", new CommentForm());

            return "boardContent";
        } catch(Exception e){
            return "error/500.html";
        }

    }

    // 좋아요 기능 구현(좋아요 버튼 누르면 실행)
    @PostMapping("/board/like")
    public RedirectView like(LikeForm likeForm, Model model){

        Long memberId = likeForm.getMemberId();

        Long boardId = likeForm.getBoardId();

        boardService.boardLike(boardId, memberId);

        return new RedirectView("/board/" + boardId + "/get");
    }

    // 좋아요 누른 사람들 조회
    @GetMapping(value = "/board/{boardId}/likeList")
    public String getLikeList(@PathVariable("boardId") Long boardId, Model model){

        try{

            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            List<Long> likeIdList = boardService.findLikeMemberList(boardId);

            List<FollowForm> followFormList = new ArrayList<>();

            for(Long L : likeIdList){

                Member member = memberService.findMemberById(L).get();
                Boolean followIsPresent = memberService.followIsPresent(authenticationMember.get().getId(), L);

                FollowForm followForm = new FollowForm();

                followForm.setMember(member);
                followForm.setFollowIsPresent(followIsPresent);

                followFormList.add(followForm);
            }

            model.addAttribute("authMember", authenticationMember.get());
            model.addAttribute("memberList", followFormList);

            return "memberList";
        } catch(Exception e){
            return "error/500";
        }
    }

    @PostMapping(value="/board/comment")
    public RedirectView addComment(@ModelAttribute("commentForm") CommentForm commentForm, Model model){

        try{

            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            if(authenticationMember.isEmpty()){
                throw new Exception();
            }

            boardService.addComment(commentForm.getBoardId(), authenticationMember.get(), commentForm.getContent());

            return new RedirectView ("/board/" + commentForm.getBoardId() + "/get");
        } catch(Exception e){
            return new RedirectView("error/500");
        }
    }
}
