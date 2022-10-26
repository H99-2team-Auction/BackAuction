package com.mini.auction.dto.response;

import com.mini.auction.domain.Product;
import lombok.AllArgsConstructor;
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
        private String path;
//        private MultipartFile[] multipartFiles;
        private String username;
        private Integer highPrice;

        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Boolean isSold;


        public CommonProductResponseDto(Product savedProduct) {
            this.id = savedProduct.getId();
            this.title = savedProduct.getTitle();
            this.username = savedProduct.getMember().getUsername();
            this.lowPrice = savedProduct.getLowPrice();
            this.highPrice = savedProduct.getHighPrice();
            this.content = savedProduct.getContent();
            this.createdAt = savedProduct.getCreatedAt();
            this.modifiedAt = savedProduct.getModifiedAt();
            this.isSold = savedProduct.getIsSold();
            this.path = savedProduct.getPath();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetailResponseDto {
        private String title;
        private Integer lowPrice;
        private Integer highPrice;

        //        private MultipartFile[] multipartFiles;
        private String username;
        private String content;
        private Boolean isSold;

        private List<CommentResponseDto> comments;
        private List<String> participants;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public ProductDetailResponseDto(Product findProduct,
                                        List<CommentResponseDto> commentsResponseDto,
                                        List<String> participants) {
            this.title = findProduct.getTitle();
            this.username = findProduct.getMember().getUsername();
            this.lowPrice = findProduct.getLowPrice();
            this.highPrice = findProduct.getHighPrice();
            this.content = findProduct.getContent();
            this.isSold = findProduct.getIsSold();
            this.createdAt = findProduct.getCreatedAt();
            this.modifiedAt = findProduct.getModifiedAt();
            this.participants = participants;

            this.comments = commentsResponseDto;
        }
    }
}