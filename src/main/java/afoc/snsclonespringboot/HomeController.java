package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
//        return "redirect:/login";
        return "redirect:/test";
    }

    @GetMapping("/test")
    public String test() {
        Member member1 = Member.builder()
                .email("test@test.com")
                .password("1234")
                .username("test_user")
                .build();
        memberService.join(member1);

        for (int i=0;i<10;i++){
            Member member = Member.builder()
                    .email("test"+i+"@test.com")
                    .password("1234")
                    .username("test_user"+i)
                    .build();
            memberService.join(member);
        }

        for (int i=0;i<10;i++){
            Board board = Board.builder()
                    .memberId(1L)
                    .build();
            boardService.upload(board);
        }

        return "test.html";
    }

    @GetMapping("/main")
    public String main(@RequestParam String email, Model model) {
//        try {
//            List<Board> boardList = boardService.findBoardListByMemberId(id);
//            Optional<Member> member = memberService.findMemberById(id);
//
//            model.addAttribute("member", member);
//            model.addAttribute("boardList", boardList);
//            return "main.html";
//        } catch (Exception e){
//            return "error/500.html";
//        }

        //List<Board> boardList = boardService.findBoardListByMemberId(id);
        //Optional<Member> member = memberService.findMemberById(id);

        /*
        model.addAttribute("member", member);
        model.addAttribute("boardList", boardList);
        */

        return "main.html";

    }

}
