package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.JpaBoardRepository;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.JpaMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Rollback(false)
public class RepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testMemberRepository(){

        JpaMemberRepository jmr = new JpaMemberRepository(em);
        JpaBoardRepository jbr = new JpaBoardRepository(em);

        /*
        Member member1 = new Member("KSM", "1234", "ksm", "ss@email.com", "..");
        Member member2 = new Member("sss", "22" ,"ddd", "dd@email.com" , "...");

        Board board1 = new Board();
        Board board2 = new Board();
        Board board3 = new Board();
        Board board4 = new Board();

        jmr.save(member1);
        jmr.save(member2);

        jbr.save(board1);
        jbr.save(board2);
        jbr.save(board3);
        jbr.save(board4);

        jmr.setBoardItem(member1, board1);
        jmr.setBoardItem(member2, board2);
        jmr.setBoardItem(member2, board3);
        jmr.setBoardItem(member2, board4);

        List<Board> boardList1 = jmr.findBoardListByMemberId(member1.getId());
        List<Board> boardList2 = jmr.findBoardListByMemberId(member2.getId());

         */

        List<Board> boardList1 = jmr.findBoardListByMemberId(3L);
        List<Board> boardList2 = jmr.findBoardListByMemberId(4L);

        System.out.println("==== member1 boardList ====");
        for(Board board : boardList1){
            System.out.println("ID : " + board.getId());
        }

        System.out.println("==== member2 boardList ====");
        for(Board board : boardList2){
            System.out.println("ID : " + board.getId());
        }

    }
}
