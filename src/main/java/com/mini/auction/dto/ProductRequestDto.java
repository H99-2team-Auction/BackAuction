package com.mini.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

public class ProductRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductRequestPostDto {

        @NotBlank
        private String title;

        private Integer lowprice;
//        private MultipartFile[] multipartFiles;

        @NotBlank
        @Lob
        private String content;

        private boolean isSold;
    }
}
