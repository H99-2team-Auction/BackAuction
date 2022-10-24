package com.mini.auction.dto.response;

import com.mini.auction.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommonProductResponseDto {

        private Long id;


        private String title;
        private Integer lowPrice;
//        private MultipartFile[] multipartFiles;
//        private String username;

        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private boolean isSold;

        public CommonProductResponseDto(Product savedProduct) {
            this.id = savedProduct.getId();


            this.title = savedProduct.getTitle();
            this.lowPrice = savedProduct.getLowPrice();
            this.content = savedProduct.getContent();
            this.createdAt = savedProduct.getCreatedAt();
            this.modifiedAt = savedProduct.getModifiedAt();
            this.isSold = savedProduct.isSold();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetailResponseDto {
        private String title;
        private Integer lowPrice;
//        private MultipartFile[] multipartFiles;
//        private String username;
        private String content;
        private boolean isSold;

        private List<CommentResponseDto> comments;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public ProductDetailResponseDto(Product findProduct, List<CommentResponseDto> commentsResponseDto) {
            this.title = findProduct.getTitle();
            this.lowPrice = findProduct.getLowPrice();
            this.content = findProduct.getContent();
            this.isSold = findProduct.isSold();
            this.createdAt = findProduct.getCreatedAt();
            this.modifiedAt = findProduct.getModifiedAt();

            this.comments = commentsResponseDto;
        }
    }
}