package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.like.likeForm;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    private final MemberServiceImpl memberService;

    @GetMapping(value="/board/{boardId}/get")
    public String visitBoardForm(@PathVariable("boardId") Long boardId, Model model){

        Optional<Member> member = getAuthenticationMember();

        Board board = boardService.findBoardByBoardId(boardId).get();

        BoardForm form = new BoardForm();

        form.setBoardId(board.getBoardId());
        form.setUsername(memberService.findMemberById(board.getMemberId()).get().getUsername());
        //form.setImageDataId(board.getImageDataId());
        //form.setTextDataId(board.getTextDataId());
        form.setRegTime(board.getRegTime());
        form.setLikeIsPresent(boardService.likeIsPresent(boardId, member.get().getId()));

        model.addAttribute("form", form);
        model.addAttribute("member", member.get());

        return "boardContent";
    }

    // 좋아요 기능 구현(좋아요 버튼 누르면 실행)
    @PostMapping("/board/{boardId}/like")
    public RedirectView like(@PathVariable("boardId") Long boardId, likeForm likeForm){

        Long memberId = likeForm.getMemberId();

        boardService.boardLike(boardId, memberId);

        return new RedirectView("/board/" + boardId + "/get");
    }

    // 좋아요 누른 사람들 조회
    @GetMapping(value = "/board/{boardId}/likeList")
    public String getLikeList(@PathVariable("boardId") Long boardId, Model model){

        List<Long> likeMemberList = boardService.findLikeMemberList(boardId);

        List<Member> likeList;

        try{
            likeList = likeMemberList.stream().map(L -> memberService.findMemberById(L).get()).collect(Collectors.toList());
        } catch(NoSuchElementException noSuchElementException){
            likeList = null;
        }

        Optional<Member> member = getAuthenticationMember();

        model.addAttribute("likeList", likeList);
        model.addAttribute("member", member.get());

        return "likeList";
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
