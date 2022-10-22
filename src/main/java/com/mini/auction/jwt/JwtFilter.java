package com.mini.auction.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
// OncePerRequestFilter = 사용자의 요청 한번에 한번만 실행되는 필터를 생성한다 = "필터결과를 재활용 한다"
public class JwtFilter extends OncePerRequestFilter {

    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String REFRESH_TOKEN_HEADER = "Refresh-Token";
    public static String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;


    //security context에 저장하여 사용
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //토큰 선언
        String jwt = resolveToken(request);

    //hasText = 문자가 유효한지 체크하는 메소드 (공백을 제외하고 길이가 1이상인경우 true 값을 내보냄)
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
    //토큰 추출 메소드
    //헤더에서 토큰을 가져와 앞7자리 "bearer "를 때낸 뒤 토큰값을 반환
    private String resolveToken(HttpServletRequest request) {
        String Token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(Token) && Token.startsWith(BEARER_PREFIX)) {
            return Token.substring(7);
        }
        return null;
    }
}