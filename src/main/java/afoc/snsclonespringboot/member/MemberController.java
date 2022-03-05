package afoc.snsclonespringboot.member;

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
