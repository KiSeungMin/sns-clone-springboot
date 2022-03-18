package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm){
        Optional<Member> foundMember = memberService.findMemberByEmail(loginForm.getEmail());
        if (foundMember.isPresent() &&
                loginForm.getPassword().equals(foundMember.get().getPassword())) {
            return "redirect:/main";
        } else {
            return "redirect:/login-failed";
        }

    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "signup";
    }


    @PostMapping("/signup")
    public String signup(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "signup";
        }

        try{

            Member member = Member.builder()
                    .username(memberFormDto.getUsername())
                    //.password(memberFormDto.getPassword())
                    .password(passwordEncoder.encode(memberFormDto.getPassword()))
                    .email(memberFormDto.getEmail())
                    .role(Role.USER)
                    .build();

            memberService.join(member);

        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/login-failed")
    public String loginFailed(Model model) {

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/login";
    }

    @GetMapping("/signup-failed")
    public String singupFailed() {
        return "signup-failed.html";
    }

}