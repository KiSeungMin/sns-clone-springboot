package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BoardService boardService;
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

        } catch(Error e){
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

    @PostMapping("/member/{memberId}/follow")
    public RedirectView follow(@PathVariable("memberId") Long memberId){

        Long followerId = memberService.getAuthenticationMember().get().getId();

        Long followeeId = memberId;

        memberService.follow(followerId, followeeId);

        return new RedirectView( "/member/" + memberId + "/memberPage");
    }

    @GetMapping("/member/{memberId}/followerList")
    public String followerList(@PathVariable("memberId") Long memberId, Model model){
        // Get auth member used in header
        MemberShowForm authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        try {
            List<Long> followerIdList = memberService.findFollowers(memberId);

            List<FollowForm> followFormList = new ArrayList<>();

            Long userId = memberService.getAuthenticationMember().get().getId();

            for(Long L : followerIdList){

                Member member = memberService.findMemberById(L).get();
                Boolean followIsPresent = memberService.followIsPresent(userId, L);

                FollowForm followForm = new FollowForm();

                followForm.setMember(member);
                followForm.setFollowIsPresent(followIsPresent);

                followFormList.add(followForm);
            }

            model.addAttribute("member", authMember);
            model.addAttribute("memberList", followFormList);
        } catch (Exception e){
            return "error/500.html";
        }



        return "memberList";
    }

    @GetMapping("/member/{memberId}/followeeList")
    public String followeeList(@PathVariable("memberId") Long memberId, Model model){
        // Get auth member used in header
        MemberShowForm authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        try{
            List<Long> followeeIdList = memberService.findFollowees(memberId);

            List<FollowForm> followFormList = new ArrayList<>();

            Long userId = memberService.getAuthenticationMember().get().getId();

            for(Long L : followeeIdList){

                Member member = memberService.findMemberById(L).get();
                Boolean followIsPresent = memberService.followIsPresent(userId, L);

                FollowForm followForm = new FollowForm();

                followForm.setMember(member);
                followForm.setFollowIsPresent(followIsPresent);

                followFormList.add(followForm);
            }

            model.addAttribute("member", authMember);
            model.addAttribute("memberList", followFormList);

            return "memberList";
        } catch (Exception e) {
            return "error/500.html";
        }
    }

    @GetMapping(value="/member/{memberId}/memberPage")
    public String memberPage(@PathVariable("memberId") Long memberId, Model model){
        // Get auth member used in header
        MemberShowForm authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        try {
            Member member = memberService.findMemberById(memberId).get();

            List<Board> boardList = boardService.findBoardListByMemberId(memberId);

            Boolean followIsPresent = memberService.followIsPresent(memberService.getAuthenticationMember().get().getId(), memberId);

            model.addAttribute("memberPageForm", member);
            model.addAttribute("boardList", boardList);
            model.addAttribute("member", authMember);
            model.addAttribute("followIsPresent", followIsPresent);

            return "memberPage";
        } catch (Exception e){
            return "error/500.html";
        }
    }

    public MemberShowForm getAuthMemberShowForm() throws Exception {
        // Get auth member used in header
        Optional<Member> authenticationMemberOptional = memberService.getAuthenticationMember();
        if(authenticationMemberOptional.isEmpty())
            throw new Exception();

        Member authMember = authenticationMemberOptional.get();
        Optional<DataInfo> dataInfo = dataService.load(authMember.getImageDataInfoId());
        if(dataInfo.isEmpty()){
            throw new Exception();
        }
        String profileImagePath = dataInfo.get().getSaveDataPath();

        return MemberShowForm.builder()
                .id(authMember.getId())
                .username(authMember.getUsername())
                .profileImagePath(profileImagePath)
                .build();
    }

}