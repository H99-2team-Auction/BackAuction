package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.LoginRequestDto;
import com.mini.auction.dto.request.MemberRequestDto;
import com.mini.auction.service.MemberService;
import com.mini.auction.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto){
        return memberService.registerMember(memberRequestDto);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return memberService.login(loginRequestDto, httpServletResponse);
    }

    //로그아웃

}
