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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        model.addAttribute("member", memberService.getAuthenticationMember().get());
        model.addAttribute("boardForm", new BoardForm());
        return "createBoard";
    }

    @PostMapping(value="/board/new")
    public String uploadBoard(@ModelAttribute BoardForm boardForm, Model model){
        Optional<Member> authenticationMember = memberService.getAuthenticationMember();
        if (authenticationMember.isEmpty())
            throw new IllegalStateException();

        Long memberId = authenticationMember.get().getId();

        Board board = Board.builder()
                .memberId(memberId)
                .textData(boardForm.getTextData())
                .date(new Date())
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
                Long dataInfoId = dataInfo.get().getId();

                BoardData boardData = BoardData.builder()
                        .boardId(boardId)
                        .dataInfoId(dataInfoId)
                        .build();

                boardService.uploadBoardData(boardData);
            }
        }
        return "redirect:/main";
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

        Optional<Member> member = memberService.getAuthenticationMember();

        Board board = boardService.findBoardByBoardId(boardId).get();

        BoardContentForm boardContentForm = new BoardContentForm();

        List<Comment> commentList = boardService.getCommentList(boardId);

        boardContentForm.setBoard(board);
        boardContentForm.setFollowIsPresent(memberService.followIsPresent(member.get().getId(), board.getMemberId()));
        boardContentForm.setLikeIsPresent(boardService.likeIsPresent(board.getMemberId(), member.get().getId()));

        model.addAttribute("boardContentForm", boardContentForm);
        model.addAttribute("member", member.get());
        model.addAttribute("commentList", commentList);
        model.addAttribute("likeForm", new LikeForm());
        model.addAttribute("commentForm", new CommentForm());

        return "boardContent";
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

        List<Long> likeIdList = boardService.findLikeMemberList(boardId);

        List<FollowForm> followFormList = new ArrayList<>();

        Long memberId = memberService.getAuthenticationMember().get().getId();

        for(Long L : likeIdList){

            Member member = memberService.findMemberById(L).get();
            Boolean followIsPresent = memberService.followIsPresent(memberId, L);

            FollowForm followForm = new FollowForm();

            followForm.setMember(member);
            followForm.setFollowIsPresent(followIsPresent);

            followFormList.add(followForm);
        }

        model.addAttribute("member", memberService.getAuthenticationMember().get());
        model.addAttribute("memberList", followFormList);

        return "memberList";
    }

    @PostMapping(value="/board/comment")
    public RedirectView addComment(@ModelAttribute("commentForm") CommentForm commentForm, Model model){

        Member member = memberService.getAuthenticationMember().get();

        boardService.addComment(commentForm.getBoardId(), member, commentForm.getContent());

        return new RedirectView ("/board/" + commentForm.getBoardId() + "/get");
    }
}
