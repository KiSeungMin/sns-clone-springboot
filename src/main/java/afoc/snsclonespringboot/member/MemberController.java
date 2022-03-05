package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm){
        Optional<Member> foundMember = memberService.findByEmail(loginForm.getEmail());
        if (foundMember.isPresent() &&
                loginForm.getPassword().equals(foundMember.get().getPassword())) {
            return "redirect:/main";
        } else {
            return "denied";
        }

    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberForm memberForm) {

        Member member = Member.builder()
                .username(memberForm.getUsername())
                .password(memberForm.getPassword())
                .nickname(memberForm.getNickname())
                .email(memberForm.getEmail())
                .build();

        boolean isSuccess = memberService.join(member);
        if (isSuccess) {
            return "redirect:/login";
        }
        else {
            return "denied";
        }

    }

}