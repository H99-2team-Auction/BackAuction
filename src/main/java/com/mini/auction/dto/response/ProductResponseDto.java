package com.mini.auction.dto.response;

import com.mini.auction.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommonProductResponseDto {

        private String title;
        private Integer lowprice;
//        private MultipartFile[] multipartFiles;
//        private String username;

        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private boolean isSold;

        public CommonProductResponseDto(Product savedProduct) {
            title = savedProduct.getTitle();
            lowprice = savedProduct.getLowPrice();
            content = savedProduct.getContent();
            createdAt = savedProduct.getCreatedAt();
            modifiedAt = savedProduct.getModifiedAt();
            isSold = savedProduct.isSold();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetailResponseDto {
        private String title;
        private Integer lowprice;
//        private MultipartFile[] multipartFiles;
//        private String username;
        private String content;
        private boolean isSold;

        private List<CommentResponseDto> comments;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public ProductDetailResponseDto(Product findProduct, List<CommentResponseDto> commentsResponseDto) {
            this.title = findProduct.getTitle();
            this.lowprice = findProduct.getLowPrice();
            this.content = findProduct.getContent();
            this.isSold = findProduct.isSold();
            this.createdAt = findProduct.getCreatedAt();
            this.modifiedAt = findProduct.getModifiedAt();

            this.comments = commentsResponseDto;
        }
    }
}