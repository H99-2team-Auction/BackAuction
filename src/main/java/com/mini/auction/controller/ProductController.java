package com.mini.auction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.ProductRequestDto;
import com.mini.auction.service.ProductService;
import com.mini.auction.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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
    private final MemberController memberController;

    /**
     * 상품 등록
     */
    @PostMapping
    public ResponseEntity<ResponseDto<CommonProductResponseDto>> addProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                                            @RequestPart(value = "dto") @Valid ProductRequestPostDto dto,
                                                                            @RequestParam("dto") String dto,
                                                            @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ProductRequestPostDto readDto = objectMapper.readValue(dto, new TypeReference<>() {});
        log.info("===================");
        log.info(readDto.toString());
        log.info(multipartFile.getContentType());
        log.info("===================");
        CommonProductResponseDto responseDto = productService.postProduct(userDetails.getMember(), readDto, multipartFile);
        return new ResponseEntity<>(ResponseDto.success(responseDto), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public ResponseEntity<ResponseDto<List<CommonProductResponseDto>>> getProducts() {
        List<CommonProductResponseDto> products = productService.findAllProducts();

        return new ResponseEntity<>(ResponseDto.success(products), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 상세 조회: 상품 상세 정보 And 해당 상품 댓글 전체 조회
     */
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        ProductDetailResponseDto responseDto = productService.findOneProduct(productId);
        return new ResponseEntity<>(ResponseDto.success(responseDto), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<String>> delProduct(@PathVariable Long productId) {
        String message = productService.deleteProduct(productId);
        return new ResponseEntity<>(ResponseDto.success(message), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 상품 수정
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @PathVariable Long productId,
                                                                  @RequestBody @Valid ProductRequestPostDto productRequestPostDto) {
        CommonProductResponseDto responseDto = productService.modifyProduct(userDetails.getMember(), productId, productRequestPostDto);
        return new ResponseEntity<>(ResponseDto.success(responseDto), memberController.setHeaders(), HttpStatus.OK);
    }
}

