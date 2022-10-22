package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.security.user.UserDetailsImpl;
import com.mini.auction.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/product/{productId}/like")
    public ResponseEntity<ResponseDto<String>> like(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long productId) {
        ResponseDto<String> responseDto = likeService.likeProduct(userDetails.getMember(), productId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }

}
