package afoc.snsclonespringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
// WebSecurityConfigurerAdapter를 상속받아서 보안 설정을 커스터마이징항 수 있다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // http 요청에 대한 보안 설정(페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등)
    @Override
    protected void configure(HttpSecurity http) throws Exception{


    }

    // 비밀번호를 암호화하여 저장
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
