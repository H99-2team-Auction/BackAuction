package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.security.user.UserDetailsImpl;
import com.mini.auction.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final MemberController memberController;

    @PostMapping("/product/{productId}/like")
    public ResponseEntity<ResponseDto<String>> like(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long productId) {
        ResponseDto<String> responseDto = likeService.likeProduct(userDetails.getMember(), productId);
        return new ResponseEntity<>(responseDto, memberController.setHeaders(), HttpStatus.OK);
    }

}
