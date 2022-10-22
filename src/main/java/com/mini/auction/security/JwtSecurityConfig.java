package com.mini.auction.security;

import com.mini.auction.security.jwt.JwtFilter;
import com.mini.auction.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//SecurityConfig에 TokenProvider와 JwtFilter를 적용하기 위한 클래스
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider jwtProvider;

    // TokenProvider 주입 받고 JwtFilter를 통해 securiry 로직에 필터 등록
    @Override
    public void configure(HttpSecurity httpSecurity){
        JwtFilter customFilter = new JwtFilter(jwtProvider);
        //UsernamePasswordAuthenticationFilter 보다 먼저 실행
        httpSecurity.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
