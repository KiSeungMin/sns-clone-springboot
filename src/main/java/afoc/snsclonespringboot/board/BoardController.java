package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.Comment.Comment;
import afoc.snsclonespringboot.board.Comment.CommentForm;
import afoc.snsclonespringboot.board.like.LikeForm;
import afoc.snsclonespringboot.member.FollowForm;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    private final MemberServiceImpl memberService;

    @GetMapping(value="/board/{boardId}/get")
    public String visitBoardForm(@PathVariable("boardId") Long boardId, Model model){

        Optional<Member> member = getAuthenticationMember();

        Board board = boardService.findBoardByBoardId(boardId).get();

        BoardForm boardForm = new BoardForm();

        List<Comment> commentList = boardService.getCommentList(boardId);

        boardForm.setBoardId(board.getBoardId());
        boardForm.setUsername(memberService.findMemberById(board.getMemberId()).get().getUsername());
        boardForm.setUserId(board.getMemberId());
        //form.setImageDataId(board.getImageDataId());
        //form.setTextDataId(board.getTextDataId());
        boardForm.setDate(board.getDate());
        boardForm.setFollowIsPresent(memberService.followIsPresent(member.get().getId(), board.getMemberId()));
        boardForm.setLikeIsPresent(boardService.likeIsPresent(board.getMemberId(), member.get().getId()));

        model.addAttribute("boardForm", boardForm);
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

        Long memberId = getAuthenticationMember().get().getId();

        for(Long L : likeIdList){

            Member member = memberService.findMemberById(L).get();
            Boolean followIsPresent = memberService.followIsPresent(memberId, L);

            FollowForm followForm = new FollowForm();

            followForm.setMember(member);
            followForm.setFollowIsPresent(followIsPresent);

            followFormList.add(followForm);
        }

        model.addAttribute("member", getAuthenticationMember().get());
        model.addAttribute("memberList", followFormList);

        return "memberList";
    }

    @PostMapping(value="/board/comment")
    public RedirectView addComment(@ModelAttribute("commentForm") CommentForm commentForm, Model model){

        Member member = getAuthenticationMember().get();

        boardService.addComment(commentForm.getBoardId(), member, commentForm.getContent());

        return new RedirectView ("/board/" + commentForm.getBoardId() + "/get");
    }

    // 인증 멤버를 함수로 받아서 구현
    public Optional<Member> getAuthenticationMember(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<Member> member = memberService.findMemberByEmail(email);

        if(!member.isPresent()){
            throw new Error();
        }

        return member;
    }

}
