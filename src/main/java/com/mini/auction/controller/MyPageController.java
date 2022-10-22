package com.mini.auction.controller;

import com.mini.auction.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    /**
     *  낙찰받은 상품 목록 조회
     */

//    @GetMapping("/sold")
//    public ResponseEntity<?> getSoldProductList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        List<ProductResponseDto.CommonProductResponseDto> responseDtoList = myPageService.getSoldProductList(userDetails.getMember());
//        return new ResponseEntity<>(ResponseDto.success(responseDtoList), setHeaders(), HttpStatus.OK, )
//    }

//    @GetMapping("/bid")
//    public ResponseEntity<?>


    /**
     * 헤더 객체 생성 후 데이터 반환 형식 json 으로 설정
     */
    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }
}
