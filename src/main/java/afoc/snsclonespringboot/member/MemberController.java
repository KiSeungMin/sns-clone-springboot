package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final DataService dataService;
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
    public String signup(@Valid @ModelAttribute MemberForm memberForm,
                         RedirectAttributes redirectAttributes,
                         BindingResult bindingResult,
                         Model model)
    throws IOException
    {

        if(bindingResult.hasErrors()){
            return "signup";
        }

        try{
            Optional<DataInfo> dataInfo = dataService.save(memberForm.getProfileImage(), DataType.Image);
            if(dataInfo.isEmpty())
                throw new IllegalStateException();

            Member member = Member.builder()
                    .username(memberForm.getUsername())
                    .password(passwordEncoder.encode(memberForm.getPassword()))
                    .email(memberForm.getEmail())
                    .imageDataInfoId(dataInfo.get().getId())
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
        System.out.println("MemberController.loginFailed");
        return "/login";
    }

    @GetMapping("/signup-failed")
    public String signupFailed() {
        System.out.println("MemberController.signupFailed");
        return "signup-failed";
    }

    @GetMapping("logout")
    public String logout(){
        System.out.println("MemberController.logout");
        return "/login";
    }

    public Optional<Member> getAuthenticationMember(){

        // 인증된 객체의 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {

            String email = "";

            // getName 메서드를 통해 member의 email을 가져온다. (SecurityConfig 파일에서 username parameter를 email로 설정해서 그런듯)
            email = authentication.getName();

            // getName 메서드를 통해 member의 email을 가져온다.
            Optional<Member> member = this.memberService.findMemberByEmail(email);

            return member;

        } else{
            return null;
        }
    }
}