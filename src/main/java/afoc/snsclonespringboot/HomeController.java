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
        return "login";
    }

    @GetMapping("/main")
    public String main(@RequestParam Long id, Model model) {
        try {
            List<Board> boardList = boardService.findBoardListByMemberId(id);
            Optional<Member> member = memberService.findMemberById(id);

            model.addAttribute("id", id);
            return "main.html";
        } catch (Exception e){
            return "error/500.html";
        }
    }
}
