package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "signup";
    }


    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            System.out.println("MemberController.signup");
            return "signup";
        }

        try{

            Member member = Member.builder()
                    .username(memberForm.getUsername())
                    .password(passwordEncoder.encode(memberForm.getPassword()))
                    .email(memberForm.getEmail())
                    .role(Role.USER)
                    .build();

            memberService.join(member);

        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/login-failed")
    public String loginFailed(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/login";
    }
}