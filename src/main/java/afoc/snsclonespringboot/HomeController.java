package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.BoardService;
import afoc.snsclonespringboot.board.BoardShowForm;
import afoc.snsclonespringboot.board.boarddata.BoardData;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataInfoRepository;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

            List<Member> memberList = new ArrayList<Member>();
            for (int i=1;i <= 3; i++){
                // profile image data
                DataInfo dataInfo = DataInfo.builder()
                        .dataType(DataType.Image)
                        .saveDataPath(Paths.get("test", "tmp" + i + ".jpg").toString())
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
                        //.textDataId((long) ((i % 2) + 4))
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
        try {
            // 인증된 객체의 정보 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication == null){
                throw new Exception();
            }

            // getName 메서드를 통해 member의 email을 가져온다. (SecurityConfig 파일에서 username parameter를 email로 설정해서 그런듯)
            String email = authentication.getName();

            Optional<Member> member = memberService.findMemberByEmail(email);
            if(member.isEmpty()){
                throw new Exception();
            }

            Optional<DataInfo> dataInfo = dataService.load(member.get().getImageDataInfoId());
            if(dataInfo.isEmpty()){
                throw new Exception();
            }
            String profileImagePath = dataInfo.get().getSaveDataPath();

            // TODO - 보여줄 보드 리스트 찾는 서비스 필요
            // TODO - board의 member가 본인이 아닌 작성자가 나오도록 바꾸기
            // TODO - 다른 모든 것들도 본인의 member가 model에 들어가도록 바꾸기
            List<BoardShowForm> boardShowFormList = new ArrayList<>();
            List<Board> boardList = boardService.findBoardListByMemberId(member.get().getId());
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
                    BoardShowForm boardShowForm = BoardShowForm.builder()
                            .boardId(board.getBoardId())
                            .memberId(board.getMemberId())
                            .username(memberService.findMemberById(board.getMemberId()).get().getUsername())
                            .date(board.getDate())
                            .textData(board.getTextData())
                            .imgPath(boardDataPathList)
                            .build();
                    boardShowFormList.add(boardShowForm);
                } catch (Exception ignored){
                }
            }

            model.addAttribute("boardList", boardShowFormList);
            model.addAttribute("member", member.get());
            model.addAttribute("profileImagePath", profileImagePath);

            return "main.html";
        } catch (Exception e){
            System.out.println("HomeController.main e");
            e.printStackTrace();
            return "error/500.html";
        }
    }
}