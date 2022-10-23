package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.LoginRequestDto;
import com.mini.auction.dto.request.MemberRequestDto;
import com.mini.auction.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<? extends ResponseDto<?>> registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
        return new ResponseEntity<>(memberService.signup(memberRequestDto), setHeaders(), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<? extends ResponseDto<?>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return new ResponseEntity<>(memberService.login(loginRequestDto, httpServletResponse), setHeaders(), HttpStatus.OK);
    }

    //로그아웃
    // not yey

    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return headers;
    }
}
