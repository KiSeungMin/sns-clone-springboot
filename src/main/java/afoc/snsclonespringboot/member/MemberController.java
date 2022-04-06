package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.BoardForm;
import afoc.snsclonespringboot.board.likeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        } catch(Error e){
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

    @GetMapping("/signup-failed")
    public String signupFailed() {
        return "signup-failed";
    }

    @GetMapping("logout")
    public String logout(){
        return "/";
    }

    @PostMapping("/member/{memberId}/follow")
    public String follow(@PathVariable("memberId") Long memberId){

        Long followerId = getAuthenticationMember().get().getId();

        Long followeeId = memberId;

        memberService.follow(followerId, followeeId);

        return "";
    }

    @GetMapping("/member/{memberId}/followerList")
    public String followerList(@PathVariable("memberId") Long memberId, Model model){

        List<Long> followerIdList = memberService.findFollowers(memberId);

        List<FollowForm> followFormList = new ArrayList<>();

        Long userId = getAuthenticationMember().get().getId();

        for(Long L : followerIdList){

            Member member = memberService.findMemberById(L).get();
            Boolean followIsPresent = memberService.followIsPresent(userId, L);

            FollowForm followForm = new FollowForm();

            followForm.setMember(member);
            followForm.setFollowIsPresent(followIsPresent);

            followFormList.add(followForm);
        }

        model.addAttribute("member", getAuthenticationMember().get());
        model.addAttribute("memberList", followFormList);

        return "memberList";
    }

    @GetMapping("/member/{memberId}/followeeList")
    public String followeeList(@PathVariable("memberId") Long memberId, Model model){

        List<Long> followeeIdList = memberService.findFollowees(memberId);

        List<FollowForm> followFormList = new ArrayList<>();

        Long userId = getAuthenticationMember().get().getId();

        for(Long L : followeeIdList){

            Member member = memberService.findMemberById(L).get();
            Boolean followIsPresent = memberService.followIsPresent(userId, L);

            FollowForm followForm = new FollowForm();

            followForm.setMember(member);
            followForm.setFollowIsPresent(followIsPresent);

            followFormList.add(followForm);
        }

        model.addAttribute("member", getAuthenticationMember().get());
        model.addAttribute("memberList", followFormList);

        return "memberList";
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