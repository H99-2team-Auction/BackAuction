package com.mini.auction.security;

import com.mini.auction.jwt.JwtAccessDeniedHandler;
import com.mini.auction.jwt.JwtAuthenticationEntryPoint;
import com.mini.auction.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //시큐리티 활성화
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }
    //password를 암호화 하지않으면 spring security가 접근을 허가하지 않는다.
    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        //jwt토큰을 이용해서 인증하는 경우 stateless방식을 사용하기 때문에 default 값인 csrf기능을 사용할 필요가 없다.
        http.cors();
        http.csrf().disable();

        // Spring Security는 기본적으로 세션방식(stateful) 사용
        // Json Web Token을 사용할때는 stateless(클라이언트의 정보를 서버에 저장 X)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()

                //아래경로 진입 모두 허용
                .antMatchers(HttpMethod.POST,"/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers(HttpMethod.GET, "/post/{postID}").permitAll()
                .antMatchers(HttpMethod.GET, "/comment/**").permitAll()

                //그이외에 인증 필요
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//인증
                .accessDeniedHandler(jwtAccessDeniedHandler);//인가

        //TokenProvider와 JwtFilter를 적용
        http.apply(new JwtSecurityConfig(jwtProvider));

        return http.build();
    }
}
