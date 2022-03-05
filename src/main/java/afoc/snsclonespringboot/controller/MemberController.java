package afoc.snsclonespringboot.controller;

import afoc.snsclonespringboot.domain.AccountRole;
import afoc.snsclonespringboot.domain.Member;
import afoc.snsclonespringboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // TODO
    @PostMapping("/signup")
    public String signup(MemberForm memberForm) {

        Member member = new Member(
                memberForm.getUsername(),
                memberForm.getPassword(),
                memberForm.getNickname(),
                memberForm.getEmail(),
                null,
                AccountRole.MEMBER
        );

        boolean isSuccess = true;
        //isSuccess= memberService.join(member);
        if (isSuccess) {
            return "redirect:/";
        }
        else {
            return "";
        }

    }

}
*/
