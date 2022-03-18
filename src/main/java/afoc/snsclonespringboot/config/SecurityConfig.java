package afoc.snsclonespringboot.config;

import afoc.snsclonespringboot.member.MemberService;
import afoc.snsclonespringboot.member.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
// WebSecurityConfigurerAdapter를 상속받아서 보안 설정을 커스터마이징항 수 있다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberServiceImpl memberService;

    // http 요청에 대한 보안 설정(페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등)
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.formLogin()
                .loginPage("/login")        // 로그인 페이지 URL 설정
                .defaultSuccessUrl("/main")     // 로그인 성공 시 이동할 URL
                .usernameParameter("email")     // 로그인 시 사용할 파라미터 이름
                .failureUrl("/login-failed")        // 로그인 실패 시 이동할 이동할 URL
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))     // 로그아웃 성공 시 이동할 URL
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    // 비밀번호를 암호화하여 저장
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
