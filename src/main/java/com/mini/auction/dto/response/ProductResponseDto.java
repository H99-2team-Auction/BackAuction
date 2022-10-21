package com.mini.auction.dto.response;

import com.mini.auction.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
            lowprice = savedProduct.getLowprice();
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

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

    }
}