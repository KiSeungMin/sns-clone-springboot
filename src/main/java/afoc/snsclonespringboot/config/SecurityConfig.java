package afoc.snsclonespringboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                        .mvcMatchers("/", "/login", "/signup").permitAll()
                        .anyRequest().authenticated()
                        .and()
                .csrf()
                        .ignoringAntMatchers("/h2-console/**")
                        .and()
                .formLogin()
                        .defaultSuccessUrl("/main")
                        .permitAll()
                        .and()
                .logout()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        // css, imgae, js 등 정적 리소스는 보안필터를 거치지 않게 한다.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
