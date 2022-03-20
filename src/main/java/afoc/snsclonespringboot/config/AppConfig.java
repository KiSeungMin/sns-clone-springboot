package afoc.snsclonespringboot.config;

import afoc.snsclonespringboot.board.*;
import afoc.snsclonespringboot.board.like.JpaLikeRepository;
import afoc.snsclonespringboot.board.like.LikeRepository;
import afoc.snsclonespringboot.board.like.MemoryLikeRepository;
import afoc.snsclonespringboot.member.*;
import afoc.snsclonespringboot.member.follow.FollowRepository;
import afoc.snsclonespringboot.member.follow.JpaFollowRepository;
import afoc.snsclonespringboot.member.follow.MemoryFollowRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Persistent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class AppConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository(), followRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JpaMemberRepository(em);
    }

    @Bean
    public FollowRepository followRepository() {
        return new MemoryFollowRepository();
//        return new JpaFollowRepository(em);
    }

    @Bean
    public BoardService boardService() {
        return new BoardServiceImpl(boardRepository(), likeRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
//        return new MemoryBoardRepository();
        return new JpaBoardRepository(em);
    }

    @Bean
    public LikeRepository likeRepository(){
//        return new MemoryLikeRepository();
        return new JpaLikeRepository(em);
    }
}
