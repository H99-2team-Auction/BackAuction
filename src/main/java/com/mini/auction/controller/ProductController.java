package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.service.ProductService;
import com.mini.auction.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.mini.auction.dto.request.ProductRequestDto.*;
import static com.mini.auction.dto.response.ProductResponseDto.*;


/**
 * 모든 예외처리 RuntimeException
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록
     */
    @PostMapping
    public ResponseEntity<ResponseDto<CommonProductResponseDto>> addProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                            @RequestBody @Valid ProductRequestPostDto productRequestPostDto) {
        CommonProductResponseDto responseDto = productService.postProduct(
                userDetails.getMember(),
                productRequestPostDto);
        return new ResponseEntity<>(ResponseDto.success(responseDto), setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public ResponseEntity<ResponseDto<List<CommonProductResponseDto>>> getProducts() {
        List<CommonProductResponseDto> products = productService.findAllProducts();

        return new ResponseEntity<>(ResponseDto.success(products), setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 상세 조회: 상품 상세 정보 And 해당 상품 댓글 전체 조회
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseDto> getProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.findOneProduct(productId), setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<String>> delProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 수정
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<CommonProductResponseDto> updateProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @PathVariable Long productId,
                                                                  @RequestBody @Valid ProductRequestPostDto productRequestPostDto) {
        CommonProductResponseDto responseDto = productService.modifyProduct(userDetails.getMember(), productId, productRequestPostDto);
        return new ResponseEntity<>(responseDto, setHeaders(), HttpStatus.OK);
    }


    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }


}

