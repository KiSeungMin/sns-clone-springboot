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

            // ------ Add test data in dataInfo -------------
            System.out.println("Start set dataInfos");
            List<DataInfo> dataInfoList = new ArrayList<>();
            for(int i=0;i<25;i++){
                DataInfo dataInfo = DataInfo.builder()
                        .dataType(DataType.Image)
                        .saveDataPath(Paths.get("test", "tmp" + (i%25+1) + ".jpg").toString())
                        .build();

                dataInfoRepository.save(dataInfo);
                dataInfoList.add(dataInfo);
            }
            System.out.println("Num dataInfo = " + dataInfoList.size());


            // ------------ Add 100 members -------------
            System.out.println("Start set members");
            List<Member> memberList = new ArrayList<>();
            for (int i=1;i <= 100; i++){
                 // member
                Member member = Member.builder()
                        .email("test" + i + "@test.com")
                        .password(passwordEncoder.encode("1234"))
                        .username("test" + i + "_user")
                        .imageDataInfoId(dataInfoList.get(i%25).getId())
                        .role(Role.USER)
                        .build();
                memberService.join(member);
                memberList.add(member);
            }
            System.out.println("Num member = " + memberList.size());


            // ------------ Follow each other, randomly -------------
            System.out.println("Start set follows");
            int cnt = 0;
            for (Member member1 : memberList){
                for (int i=0; i<(int)(Math.random()*30); i++){
                    int j = (int)(Math.random()*memberList.size());
                    Member member2 = memberList.get(j);
                    memberService.follow(member1.getId(), member2.getId());
                    cnt ++;
                }
            }
            System.out.println("Num follow = " +cnt);


            // Add Boards as random text and Add BoardData as random img
            System.out.println("Start set boards");
            List<Board> boardList = new ArrayList<>();
            for (Member member : memberList) {
                for (int i=0; i<(int)(Math.random()*30); i++){
                    Board board = Board.builder()
                            .textData(getRandomText(900))
                            .date(new Date())
                            .memberId(member.getId())
                            .build();
                    boardService.upload(board);
                    boardList.add(board);

                    for (int j=0; j<1+(int)(Math.random()*5); j++){
                        BoardData boardData = BoardData.builder()
                                .boardId(board.getBoardId())
                                .dataInfoId(dataInfoList.get((int)(Math.random()*25)).getId())
                                .build();

                        boardService.uploadBoardData(boardData);
                    }
                }
            }
            System.out.println("Num boards = " + boardList.size());


            // ------------ Add like randomly -------------
            System.out.println("Start set like");
            int cnt2 = 0;
            for (Member member : memberList){
                for(int i=0; i<(int)(Math.random()*50); i++){
                    int j = (int)(Math.random()*boardList.size());
                    boardService.likeBoard(boardList.get(j).getBoardId(), member.getId());
                    cnt2 ++;
                }
            }
            System.out.println("Num like = " + cnt2);

            // ------------ Add comments randomly -------------
            System.out.println("Start set comments");
            int cnt3 = 0;
            for (Board board : boardList){
                for(int i=0;i<(int)(Math.random()*20);i++){
                    boardService.addComment(
                            board.getBoardId(),
                            memberList.get((int)(Math.random()*memberList.size())),
                            getRandomText(300)
                    );
                    cnt3 ++;
                }
            }
            System.out.println("Num Comments = " + cnt3);
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

    public String getRandomText(int maxLength){
        StringBuilder ret = new StringBuilder();

        List<String> paragraph = new ArrayList<>();
        paragraph.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eget viverra nibh, eu lobortis erat. Sed vehicula lectus quis nibh pretium, imperdiet cursus erat semper. Morbi eget pharetra enim. Maecenas convallis augue libero, id egestas libero iaculis eu. Mauris placerat ligula in nisl facilisis, vitae venenatis tortor dictum. Maecenas aliquet lectus urna, eget lobortis libero mollis eget. Suspendisse rutrum lectus ut augue finibus facilisis. Integer dignissim dictum enim, non fringilla dolor gravida in. Nulla dignissim sit amet metus at vulputate. Quisque eget varius justo, non lacinia sem. Vivamus volutpat ullamcorper magna, sit amet feugiat neque eleifend id. Curabitur vitae sem commodo, sodales nibh a, bibendum quam. Curabitur vitae lectus at sapien lobortis fringilla.");
        paragraph.add("Curabitur laoreet tortor neque, a condimentum justo venenatis vel. Aenean feugiat ornare turpis quis facilisis. Sed semper massa et magna mattis pellentesque. Suspendisse risus risus, fermentum vitae finibus et, condimentum sed diam. Donec rutrum aliquam elit, quis euismod diam sollicitudin id. Ut a nisl nec odio rutrum suscipit ac quis ipsum. Curabitur tortor orci, gravida sit amet turpis ut, iaculis dictum enim. Vestibulum eget risus a massa vehicula consectetur a quis magna. Curabitur sollicitudin, enim eget pulvinar tincidunt, turpis lectus eleifend mi, sit amet accumsan metus nunc blandit tellus. Donec gravida ornare tellus, a facilisis magna laoreet eu. Cras commodo neque justo, et rutrum ante volutpat et. Aliquam lacus lacus, auctor ut orci nec, aliquam tempus orci. Nunc ac ipsum velit. Aenean et fringilla leo. Nam finibus augue turpis, nec imperdiet odio tincidunt non.");
        paragraph.add("Donec non imperdiet lectus, eu viverra leo. Sed facilisis massa ut rutrum elementum. Sed porta pharetra accumsan. Aenean risus dolor, tempus sit amet arcu at, gravida gravida nisl. Nunc volutpat vitae erat pellentesque lacinia. In non ultricies turpis, et varius tortor. Quisque vestibulum sollicitudin lorem, sit amet placerat purus venenatis non. Vivamus a consectetur nunc, ac luctus ipsum. Donec at vestibulum sapien. Quisque vitae sapien eget magna rutrum venenatis at nec ante. Curabitur ullamcorper sem non ipsum malesuada, eget pellentesque magna mattis. Morbi facilisis ultrices placerat. Curabitur in laoreet dui. Sed suscipit enim ipsum, ut placerat nisl sollicitudin in. Sed aliquam quis erat a malesuada. Quisque leo nisl, euismod non lacinia id, sodales id ex.");
        paragraph.add("Morbi et odio gravida, efficitur urna sit amet, viverra quam. Nullam eu condimentum lacus. Morbi ac ligula porta, efficitur mauris non, ultricies leo. Donec viverra ultricies vestibulum. Sed quam dui, semper in molestie sed, fringilla vel lacus. Pellentesque et nibh elementum dui faucibus egestas. In hac habitasse platea dictumst. Curabitur dictum lacinia massa eu posuere. Pellentesque blandit varius hendrerit.");
        paragraph.add("Pellentesque sit amet odio a leo fermentum accumsan. Sed porttitor finibus erat. Integer porttitor eleifend metus ut rutrum. In at diam condimentum, varius est eget, placerat justo. Nunc sit amet neque in elit finibus elementum. Aliquam auctor leo sed dictum fermentum. Suspendisse non ipsum maximus quam interdum interdum. Proin nec ornare dolor. Donec ac velit ut purus aliquet volutpat.");
        paragraph.add("Proin a egestas arcu. Cras dictum justo lorem, a vestibulum dolor commodo eget. Integer ac est interdum, lacinia quam id, commodo velit. Nunc tristique fermentum pulvinar. Fusce tincidunt suscipit ante. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed neque lorem, ultrices a porta sed, pulvinar sed lorem. Phasellus sollicitudin mauris non libero ultrices, imperdiet consectetur mauris auctor.");
        paragraph.add("Nullam euismod varius facilisis. Nullam porttitor lectus lacus, ac ullamcorper nunc feugiat non. Nulla finibus faucibus neque. Duis et velit quis lorem scelerisque dictum mattis eget mi. Vivamus aliquet in dolor sit amet elementum. Vivamus mollis nulla eget orci posuere, ac scelerisque quam tristique. Sed bibendum eros vitae turpis convallis, et gravida quam volutpat. Pellentesque quis mollis justo.");
        paragraph.add("Donec luctus massa id lorem hendrerit, nec tempor eros rhoncus. Quisque neque tortor, auctor id porta at, ornare eget risus. Mauris bibendum libero ut tellus rhoncus vulputate. Curabitur at lorem a ante lacinia venenatis vel at diam. Duis sed lacinia eros. Morbi vitae elit vel leo eleifend rhoncus vitae eget orci. Proin pretium, orci sed facilisis aliquam, dui elit pellentesque libero, vitae elementum velit felis vitae felis.");
        paragraph.add("Sed viverra tincidunt lectus vitae varius. Morbi blandit, nibh elementum vehicula elementum, odio tellus auctor augue, vel eleifend nisl ex ac urna. Duis in malesuada risus. Morbi dapibus sapien at est euismod congue. Vivamus eget pulvinar urna. Vivamus ut metus mollis, volutpat eros sed, tincidunt lorem. Proin hendrerit neque vitae ante pharetra iaculis. Ut malesuada mi sed tempor tempor. Phasellus tincidunt tincidunt neque, ac accumsan ipsum faucibus imperdiet.");
        paragraph.add("Praesent sodales, arcu a egestas gravida, neque velit mattis metus, id feugiat neque urna vitae mi. Aenean gravida nibh aliquet, consectetur mauris ac, fringilla ante. Nunc luctus nulla non ante varius pharetra. Duis nulla augue, fermentum nec nunc ut, sollicitudin mollis nunc. Pellentesque laoreet vitae libero non gravida. Ut rhoncus volutpat leo, a tempus velit hendrerit non. Curabitur laoreet mauris sit amet sem maximus, ut laoreet metus facilisis. Nunc luctus, metus eget dignissim porttitor, augue ex venenatis lacus, nec facilisis elit nisi sit amet metus. Nullam consectetur dignissim nulla id pharetra. Maecenas bibendum quis justo vel aliquam.");

        for(int i = (int)(Math.random() * 5); i>=0 ; i--){
            int j = (int)(Math.random() * 10);
            ret.append(paragraph.get(j));
        }

        String s = ret.toString();
        return s.substring(0, Math.min(s.length(), maxLength));
    }
}