package com.mini.auction.service;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.LoginRequestDto;
import com.mini.auction.dto.request.MemberRequestDto;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.RefreshToken;
import com.mini.auction.repository.MemberRepository;
import com.mini.auction.repository.RefreshTokenRepository;
import com.mini.auction.security.jwt.JwtUtil;
import com.mini.auction.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberReqDto) {

        // username 중복 검사
        usernameDuplicateCheck(memberReqDto);

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPassword()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return ResponseDto.success("Success signup");
    }

    public void usernameDuplicateCheck(MemberRequestDto memberReqDto) {
        if(memberRepository.findByUsername(memberReqDto.getUsername()).isPresent()){
            throw new RuntimeException("Overlap Check"); // ex) return ResponseDto.fail()
        }
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto loginReqDto, HttpServletResponse response) {

        Member member = memberRepository.findByUsername(loginReqDto.getUsername()).orElseThrow(
                () -> new RuntimeException("Not found Member")
        );

        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUsername());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(loginReqDto.getUsername());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getUsername());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return ResponseDto.success("Success Login");

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

}
