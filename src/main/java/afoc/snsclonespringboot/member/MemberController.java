package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
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
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberForm memberForm,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()){
            return "/signup";
        }

        try{
            Optional<DataInfo> dataInfo = dataService.save(memberForm.getProfileImage(), DataType.Image);
            if(dataInfo.isEmpty()) {
                throw new IllegalStateException("사진 정보가 없습니다!");
            }

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

            return "/signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/login-failed")
    public String loginFailed(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        //System.out.println("MemberController.loginFailed");
        return "/login";
    }

    /*
    @GetMapping("/signup-failed")
    public String signupFailed() {
        System.out.println("MemberController.signupFailed");
        return "signup-failed";
    }

     */

    @GetMapping("logout")
    public String logout(){
        System.out.println("MemberController.logout");
        return "/login";
    }

    @PostMapping("/member/{memberId}/follow")
    public RedirectView follow(@PathVariable("memberId") Long memberId){

        try{
            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            if(authenticationMember.isEmpty()){
                throw new Exception();
            }

            Long followerId = authenticationMember.get().getId();

            Long followeeId = memberId;

            memberService.follow(followerId, followeeId);

            return new RedirectView( "/member/" + memberId + "/memberPage");

        } catch(Exception e){
            return new RedirectView("error/500");
        }
    }

    @GetMapping("/member/{memberId}/followerList")
    public String followerList(@PathVariable("memberId") Long memberId, Model model){

        try{
            List<Long> followerIdList = memberService.findFollowers(memberId);

            List<FollowForm> followFormList = new ArrayList<>();

            Optional<Member> authenticationMember =  memberService.getAuthenticationMember();

            for(Long L : followerIdList){

                Member member = memberService.findMemberById(L).get();
                Boolean followIsPresent = memberService.followIsPresent(authenticationMember.get().getId(), L);

                FollowForm followForm = new FollowForm();

                followForm.setMember(member);
                followForm.setFollowIsPresent(followIsPresent);

                followFormList.add(followForm);
            }

            model.addAttribute("memberList", followFormList);
            model.addAttribute("authMember", authenticationMember.get());

            return "memberList";
        } catch(Exception e){
            return "error/500";
        }
    }

    @GetMapping("/member/{memberId}/followeeList")
    public String followeeList(@PathVariable("memberId") Long memberId, Model model){

        try{
            List<Long> followeeIdList = memberService.findFollowees(memberId);

            List<FollowForm> followFormList = new ArrayList<>();

            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            for(Long L : followeeIdList){

                Member member = memberService.findMemberById(L).get();
                Boolean followIsPresent = memberService.followIsPresent(authenticationMember.get().getId(), L);

                FollowForm followForm = new FollowForm();

                followForm.setMember(member);
                followForm.setFollowIsPresent(followIsPresent);

                followFormList.add(followForm);
            }

            model.addAttribute("authMember", authenticationMember.get());
            model.addAttribute("memberList", followFormList);

            return "memberList";
        } catch(Exception e){
            return "error/500";
        }
    }

    @GetMapping(value="/member/{memberId}/memberPage")
    public String memberPage(@PathVariable("memberId") Long memberId, Model model){

        try{
            Member member = memberService.findMemberById(memberId).get();

            List<Board> boardList = boardService.findBoardListByMember(member);

            Optional<Member> authenticationMember = memberService.getAuthenticationMember();

            Boolean followIsPresent = memberService.followIsPresent(authenticationMember.get().getId(), memberId);

            model.addAttribute("memberPageForm", member);
            model.addAttribute("boardList", boardList);
            model.addAttribute("authMember", authenticationMember.get());
            model.addAttribute("followIsPresent", followIsPresent);

            return "memberPage";
        } catch(Exception e){
            return "error/500";
        }

    }
}