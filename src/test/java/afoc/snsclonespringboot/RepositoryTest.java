package afoc.snsclonespringboot;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.board.JpaBoardRepository;
import afoc.snsclonespringboot.board.like.JpaLikeRepository;
import afoc.snsclonespringboot.board.like.Like;
import afoc.snsclonespringboot.member.Member;
import afoc.snsclonespringboot.member.JpaMemberRepository;
import afoc.snsclonespringboot.member.follow.Follow;
import afoc.snsclonespringboot.member.follow.JpaFollowRepository;
import org.junit.jupiter.api.Assertions;
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
        JpaLikeRepository jlr = new JpaLikeRepository(em);
        JpaFollowRepository jfr = new JpaFollowRepository(em);

        Member member1 = Member.builder()
                .email("a")
                .password("a")
                .username("a")
                .build();

        Member member2 = Member.builder()
                .email("b")
                .password("b")
                .username("b")
                .build();

        jmr.save(member1);
        jmr.save(member2);

        Board board1 = Board.builder().build();
        Board board2 = Board.builder().build();

        jbr.save(board1);
        jbr.save(board2);

        board1.setMemberId(member1.getId());
        board2.setMemberId(member2.getId());

        Like like1 = Like.builder()
                .boardId(board1.getBoardId())
                .memberId(member1.getId())
                .build();
        Like like2 = Like.builder()
                .boardId(board1.getBoardId())
                .memberId(member2.getId())
                .build();

        jlr.save(like1);
        jlr.save(like2);
        
        List<Long> findLikeList = jlr.findLikeMemberListByBoardId(board1.getBoardId());

        for(Long l : findLikeList){
            System.out.println("ID : " + l);
        }

        Follow follow1 = Follow.builder()
                .followerId(member1.getId())
                .followeeId(member2.getId())
                .build();

        Follow follow2 = Follow.builder()
                .followerId(member2.getId())
                .followeeId(member1.getId())
                .build();

        jfr.save(follow1);
        jfr.save(follow2);

        List<Long> findFollowerList = jfr.findFolloweesByFollowerId(member1.getId());
        for(Long l : findFollowerList){
            System.out.println("Follower : " + l);
        }

        Assertions.assertEquals(member1, jmr.findMemberByMemberEmail(member1.getEmail()).get());

        jlr.deleteLike(board1.getBoardId(), member1.getId());

        jmr.deleteMemberByMemberId(member1.getId());
    }
}
