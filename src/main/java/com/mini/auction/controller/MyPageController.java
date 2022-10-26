package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.response.ProductResponseDto.CommonProductResponseDto;
import com.mini.auction.service.MyPageService;
import com.mini.auction.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberController memberController;

    /**
     * 낙찰받은 상품 목록 조회
     */
    @GetMapping("/sold")
    public ResponseEntity<?> getSoldProductList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CommonProductResponseDto> responseDtoList = myPageService.getSoldProductList(userDetails.getMember());
        return new ResponseEntity<>(ResponseDto.success(responseDtoList), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 입찰한 상품 목록 조회
     */
    @GetMapping("/bid")
    public ResponseEntity<?> getBidList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CommonProductResponseDto> responseDtoList = myPageService.getBidList(userDetails.getMember());
        return new ResponseEntity<>(responseDtoList, memberController.setHeaders(), HttpStatus.OK);
    }

    @GetMapping("/like")
    public ResponseEntity<?> getLikeList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CommonProductResponseDto> responseDtoList = myPageService.getLikeList(userDetails.getMember());
        return new ResponseEntity<>(responseDtoList, memberController.setHeaders(), HttpStatus.OK);
    }


}
