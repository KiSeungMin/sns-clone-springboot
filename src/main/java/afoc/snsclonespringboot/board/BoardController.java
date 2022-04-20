package afoc.snsclonespringboot.board;

import afoc.snsclonespringboot.board.Comment.Comment;
import afoc.snsclonespringboot.board.Comment.CommentForm;
import afoc.snsclonespringboot.board.like.LikeForm;
import afoc.snsclonespringboot.member.FollowForm;
import afoc.snsclonespringboot.board.boarddata.BoardData;
import afoc.snsclonespringboot.data.DataInfo;
import afoc.snsclonespringboot.data.DataService;
import afoc.snsclonespringboot.data.DataType;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final DataService dataService;

    @GetMapping("/main")
    public String main(Model model) {
        // Get auth member used in header
        MemberDTO authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        try {
            // Get board list to show
            // TODO - 보여줄 보드 리스트 찾는 서비스 필요
            List<BoardDTO> boardDTOList = new ArrayList<>();
            List<Board> boardList = boardService.findBoardListByMemberId(authMember.getId());

            // get all boards to show
            for(Board board : boardList){
                try{
                    boardDTOList.add(makeBoardDTO(board));
                } catch (Exception ignored){
                }
            }

            // Add attribute to model
            model.addAttribute("authMember", authMember);
            model.addAttribute("boardList", boardDTOList);

            return "main.html";
        } catch (Exception e){
            System.out.println("HomeController.main e");
            e.printStackTrace();
            return "error/500.html";
        }
    }

    @GetMapping(value="/board/new")
    public String createBoard(Model model){
        // Get auth member used in header
        MemberDTO authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        model.addAttribute("authMember", authMember);
        model.addAttribute("boardForm", new BoardForm());
        return "createBoard";
    }

    @PostMapping(value="/board/new")
    public String createBoard(@ModelAttribute BoardForm boardForm, Model model){
        Optional<Member> authenticationMember = memberService.getAuthenticationMember();
        if (authenticationMember.isEmpty())
            throw new IllegalStateException();

        Long memberId = authenticationMember.get().getId();

        Board board = Board.builder()
                .memberId(memberId)
                .textData(boardForm.getTextData())
                .date(new Date())
                .build();

        Optional<Board> optionalBoard = boardService.upload(board);
        if(optionalBoard.isEmpty())
            throw new IllegalStateException();

        Long boardId = optionalBoard.get().getBoardId();

        for (MultipartFile multipartFile : boardForm.getImageFiles()) {
            if (!multipartFile.isEmpty()){
                Optional<DataInfo> dataInfo = dataService.save(multipartFile, DataType.Image);
                if(dataInfo.isEmpty())
                    throw new IllegalStateException();
                Long dataInfoId = dataInfo.get().getId();

                BoardData boardData = BoardData.builder()
                        .boardId(boardId)
                        .dataInfoId(dataInfoId)
                        .build();

                boardService.uploadBoardData(boardData);
            }
        }
        return "redirect:/main";
    }


    @GetMapping(value="/board/{boardId}/get")
    public String visitBoardForm(@PathVariable("boardId") Long boardId, Model model){
        // Get auth member used in header
        MemberDTO authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }
        Board board;
        Member boardAuthor;
        try {
            board = boardService.findBoardByBoardId(boardId).get();
            boardAuthor = memberService.findMemberById(board.getMemberId()).get();
        } catch (Exception e){
            e.printStackTrace();
            return "error/404.html";
        }

        BoardContentDTO boardContentDTO = BoardContentDTO.builder()
                .board(makeBoardDTO(board))
                .followIsPresent(memberService.followIsPresent(authMember.getId(), boardAuthor.getId()))
                .likeIsPresent(boardService.likeIsPresent(board.getBoardId(), authMember.getId()))
                .build();

        List<Comment> commentList = boardService.getCommentList(boardId);

        model.addAttribute("authMember", authMember);
        model.addAttribute("boardContentDTO", boardContentDTO);
        model.addAttribute("commentList", commentList);
        model.addAttribute("likeForm", new LikeForm());
        model.addAttribute("commentForm", new CommentForm());

        return "boardContent";
    }

    // 좋아요 기능 구현(좋아요 버튼 누르면 실행)
    @PostMapping("/board/like")
    public RedirectView like(LikeForm likeForm, Model model){

        Long memberId = likeForm.getMemberId();

        Long boardId = likeForm.getBoardId();

        boardService.boardLike(boardId, memberId);

        return new RedirectView("/board/" + boardId + "/get");
    }

    // 좋아요 누른 사람들 조회
    @GetMapping(value = "/board/{boardId}/likeList")
    public String getLikeList(@PathVariable("boardId") Long boardId, Model model){
        // Get auth member used in header
        MemberDTO authMember;
        try {
            authMember = getAuthMemberShowForm();
        } catch (Exception e) {
            return "redirect:/login";
        }

        List<Long> likeIdList = boardService.findLikeMemberList(boardId);

        List<FollowForm> followFormList = new ArrayList<>();

        Long memberId = memberService.getAuthenticationMember().get().getId();

        for(Long L : likeIdList){

            Member member = memberService.findMemberById(L).get();
            Boolean followIsPresent = memberService.followIsPresent(memberId, L);

            FollowForm followForm = new FollowForm();

            followForm.setMember(member);
            followForm.setFollowIsPresent(followIsPresent);

            followFormList.add(followForm);
        }

        model.addAttribute("authMember", authMember);
        model.addAttribute("memberList", followFormList);

        return "memberList";
    }

    @PostMapping(value="/board/comment")
    public RedirectView addComment(@ModelAttribute("commentForm") CommentForm commentForm, Model model){

        Member member = memberService.getAuthenticationMember().get();

        boardService.addComment(commentForm.getBoardId(), member, commentForm.getContent());

        return new RedirectView ("/board/" + commentForm.getBoardId() + "/get");
    }

    public MemberDTO getAuthMemberShowForm() throws Exception {
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

        return MemberDTO.builder()
                .id(authMember.getId())
                .username(authMember.getUsername())
                .profileImgPath(profileImagePath)
                .build();
    }

    private BoardDTO makeBoardDTO(Board board) {
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

        // find writer's memberDTO
        Member writer = memberService.findMemberById(board.getMemberId()).get();
        String writerProfileImgPath = dataService.load(writer.getImageDataInfoId()).get().getSaveDataPath();
        MemberDTO memberDTO = MemberDTO.builder()
                .id(writer.getId())
                .username(writer.getUsername())
                .profileImgPath(writerProfileImgPath)
                .build();

        // return boardDTO
        return BoardDTO.builder()
                .boardId(board.getBoardId())
                .writer(memberDTO)
                .date(board.getDate())
                .textData(board.getTextData())
                .imgPath(boardDataPathList)
                .build();
    }
}
