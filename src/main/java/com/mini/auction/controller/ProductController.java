package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.nio.charset.Charset;
import java.util.List;

import static com.mini.auction.dto.ProductRequestDto.*;
import static com.mini.auction.dto.ProductResponseDto.*;


/**
 * 모든 예외처리 RuntimeException
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록
     */
    @PostMapping
    public ResponseEntity<ResponseDto<CommonProductResponseDto>> addProduct(@RequestBody @Valid ProductRequestPostDto productRequestPostDto) {
        CommonProductResponseDto responseDto = productService.postProduct(productRequestPostDto);
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
     * 상품 상세 조회
     */
    @GetMapping("/productId")
    public void getProduct(@PathVariable Long productId) {
        productService.findOneProduct(productId);

    }




    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return headers;
    }

}
