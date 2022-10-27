package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.LoginRequestDto;
import com.mini.auction.dto.request.MemberRequestDto;
import com.mini.auction.dto.response.MemberResponseDto;
import com.mini.auction.security.user.UserDetailsImpl;
import com.mini.auction.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<MemberResponseDto>> registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }

    //로그인
    @PostMapping("/api/login")
    public ResponseEntity<ResponseDto<MemberResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return memberService.login(loginRequestDto, httpServletResponse);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String message = memberService.logout(userDetails.getMember());
        return new ResponseEntity<>(ResponseDto.success(message), HttpStatus.OK);
    }

    //로그아웃
    // not yey

    @PostMapping
    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }
}
