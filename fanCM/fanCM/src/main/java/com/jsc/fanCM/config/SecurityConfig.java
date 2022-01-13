package com.jsc.fanCM.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        //super.configure(web.ignoring().mvcMatchers("/css/", "/js/","/img/","/error/","/lib/**"););
        web.ignoring().mvcMatchers("/css/", "/js/","/img/","/error/","/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests( authorize -> authorize

                .mvcMatchers( "/members/join").anonymous()
                .mvcMatchers("/articles/**").permitAll()
                .mvcMatchers("/adn/**").hasRole("ADMIN")
                .anyRequest()
                .denyAll()
        )
                .formLogin()
                    .loginPage("/member/login")
                    .loginProcessingUrl("/dologin")
                    .usernameParameter("loginId")
                    .passwordParameter("loginPw")
                    .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("members/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIOND")
                .clearAuthentication(true)

                .and()
                    .sessionManagement()
                        .invalidSessionUrl("/")
                        .maximumSessions(1) //최대 세션 개수
                        .maxSessionsPreventsLogin(true) //동시접속 차단
                        .expiredUrl("/"); //세선 만료
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
