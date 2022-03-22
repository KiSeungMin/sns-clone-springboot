package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return "/login";
    }

    /*
    @PostMapping("/login")
    public String login(LoginForm loginForm){
        Optional<Member> foundMember = memberService.findMemberByEmail(loginForm.getEmail());

        if (foundMember.isPresent() &&
                loginForm.getPassword().equals(foundMember.get().getPassword())) {
            // TODO - this code is for test

            //return "redirect:/main?id=" + foundMember.get().getId();

            //return "redirect:/main";

            //return "redirect:/main?email=" + foundMember.get().getEmail();


            return "redirect:/home";

        } else {
            return "redirect:/login-failed";
        }

    }

     */

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
                    .password(passwordEncoder.encode(memberFormDto.getPassword()))
                    .email(memberFormDto.getEmail())
                    .role(Role.USER)
                    .build();

            memberService.join(member);

        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }

        return "login";
    }

    @GetMapping("/login-failed")
    public String loginFailed(Model model) {

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/login";
    }

    @GetMapping("/signup-failed")
    public String signupFailed() {
        return "signup-failed";
    }

    @GetMapping("/home")
    public String home(Model model){

        // 인증된 객체의 정보를 가져오는듯??
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = "";

        if(authentication != null){

            // getName 메서드를 통해 member의 email을 가져온다. (SecurityConfig 파일에서 username parameter를 email로 설정해서 그런듯)
            email = authentication.getName();
        }

        Member member = memberService.findMemberByEmail(email).get();

        model.addAttribute("member", member);

        return "/home";
    }

}