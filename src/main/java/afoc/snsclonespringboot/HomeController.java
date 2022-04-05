package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder; // TODO - 제거 필요

    @GetMapping("/")
    public String test() {
        try {
            // member
            Member member1 = Member.builder()
                    .email("test@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .username("test_user")
                    .role(Role.USER)
                    .build();
            memberService.join(member1);

            // members
            for (int i = 0; i < 10; i++) {
                Member member = Member.builder()
                        .email("test" + i + "@test.com")
                        .password(passwordEncoder.encode("1234"))
                        .username("test_user" + i)
                        .role(Role.USER)
                        .build();
                memberService.join(member);
            }

            // boards
            for (int i = 0; i < 10; i++) {
                Board board = Board.builder()
                        .memberId(1L)
                        .build();
                boardService.upload(board);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "test";
    }

    @GetMapping("/main")
    public String main(Model model) {
        try {
            // 인증된 객체의 정보 가져옴
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

            model.addAttribute("boardList", boardList);
            model.addAttribute("member", member.get());
            return "main.html";
        } catch (Exception e){
            return "error/500.html";
        }
    }
}