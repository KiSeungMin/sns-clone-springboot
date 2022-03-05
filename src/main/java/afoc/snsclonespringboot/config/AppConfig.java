package afoc.snsclonespringboot.config;

import afoc.snsclonespringboot.member.MemberRepository;
import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.MemberServiceImpl;
import afoc.snsclonespringboot.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
