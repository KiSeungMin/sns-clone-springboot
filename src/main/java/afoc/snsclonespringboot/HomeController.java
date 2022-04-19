package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
import afoc.snsclonespringboot.board.BoardShowForm;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataInfoRepository;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.MemberShowForm;
import afoc.snsclonespringboot.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Paths;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    private final MemberService memberService;
    private final DataService dataService;
    private final DataInfoRepository dataInfoRepository; // TODO - 제거 필요
    private final PasswordEncoder passwordEncoder; // TODO - 제거 필요

    @Value("${isTest}")
    private boolean isTest;

    @GetMapping("/")
    public String home() {
        try {
            if(!isTest)
                return "login";

            List<Member> memberList = new ArrayList<>();
            for (int i=1;i <= 100; i++){
                // profile image data
                DataInfo dataInfo = DataInfo.builder()
                        .dataType(DataType.Image)
                        .saveDataPath(Paths.get("test", "tmp" + (i%3+1) + ".jpg").toString())
                        .build();

                dataInfoRepository.save(dataInfo);

                // member
                Member member = Member.builder()
                        .email("test" + i + "@test.com")
                        .password(passwordEncoder.encode("1234"))
                        .username("test_user")
                        .imageDataInfoId(dataInfo.getId())
                        .role(Role.USER)
                        .build();
                memberService.join(member);

                memberService.follow(1L, member.getId());
                memberService.follow(member.getId(), 1L);
                memberList.add(member);
            }


            // boards
            for (int i = 0; i < 10; i++) {
                Board board = Board.builder()
                        .textData("hello " + i)
                        .date(new Date())

                        .memberId(memberList.get(0).getId())

                        .build();
                boardService.upload(board);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "test";
    }

    @GetMapping("/main")
    public String main(Model model) {
        // Get auth member used in header
        MemberShowForm member;
        try {
            member = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        try {
            // Get board list to show
            // TODO - 보여줄 보드 리스트 찾는 서비스 필요
            List<BoardShowForm> boardShowFormList = new ArrayList<>();
            List<Board> boardList = boardService.findBoardListByMemberId(member.getId());
            for(Board board : boardList){
                try{
                    // get contents (Board, imgPath)
                    List<Long> boardDataInfoIdList = boardService.findBoardDataInfoIdByBoardId(board.getBoardId());
                    List<String> boardDataPathList = new ArrayList<>();
                    for(Long boardDataInfoId : boardDataInfoIdList){
                        Optional<DataInfo> boardDataInfo = dataService.load(boardDataInfoId);
                        if(boardDataInfo.isPresent()){
                            String boardDataPath = boardDataInfo.get().getSaveDataPath();
                            boardDataPathList.add(boardDataPath);
                        }
                    }
                    // set boardShowForm
                    Member writer = memberService.findMemberById(board.getMemberId()).get();
                    String writerProfileImgPath = dataService.load(writer.getImageDataInfoId()).get().getSaveDataPath();
                    BoardShowForm boardShowForm = BoardShowForm.builder()
                            .boardId(board.getBoardId())
                            .memberId(board.getMemberId())
                            .username(writer.getUsername())
                            .profileImgPath(writerProfileImgPath)
                            .date(board.getDate())
                            .textData(board.getTextData())
                            .imgPath(boardDataPathList)
                            .build();
                    boardShowFormList.add(boardShowForm);
                } catch (Exception ignored){
                }
            }

            // Add attribute to model
            model.addAttribute("boardList", boardShowFormList);
            model.addAttribute("member", member);

            return "main.html";
        } catch (Exception e){
            System.out.println("HomeController.main e");
            e.printStackTrace();
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