package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataType;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder; // TODO - 제거 필요

    @GetMapping("/")
    public String home() {
//        return "redirect:/login";
        return "redirect:/test";
    }

    @GetMapping("/test")
    public String test() {
        // member
        Member member1 = Member.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("1234"))
                .username("test_user")
                .role(Role.USER)
                .build();
        memberService.join(member1);

        // members
        for (int i=0;i<10;i++){
            Member member = Member.builder()
                    .email("test"+i+"@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .username("test_user"+i)
                    .role(Role.USER)
                    .build();
            memberService.join(member);
        }

        // boards
        for (int i=0;i<10;i++){
            Board board = Board.builder()
                    .memberId(1L)
                    .textDataId((long) ((i % 2)+4))
                    .imageDataId((long) ((i % 3)+1))
                    .build();
            boardService.upload(board);
        }

        return "test";
    }

    @GetMapping("/main")
    public String main(Model model) {
        try {
            // 인증된 객체의 정보를 가져오는듯??
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication == null){
                throw new Exception();
            }

            // getName 메서드를 통해 member의 email을 가져온다. (SecurityConfig 파일에서 username parameter를 email로 설정해서 그런듯)
            String email = authentication.getName();

            Optional<Member> member = memberService.findMemberByEmail(email);
            if(member.isEmpty()){
                throw new Exception();
            }

            // TODO - 보여줄 보드 리스트 찾는 서비스 필요
            List<Board> boardList = boardService.findBoardListByMemberId(member.get().getId());

            model.addAttribute("member", member.get());
            model.addAttribute("boardList", boardList);
            return "main.html";
        } catch (Exception e){
            return "error/500.html";
        }
    }
}