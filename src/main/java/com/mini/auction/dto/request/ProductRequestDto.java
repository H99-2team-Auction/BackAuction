package com.mini.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Getter
public class ProductRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ProductRequestPostDto {

        @NotBlank
        private String title;

        private Integer lowPrice;
//        private MultipartFile[] multipartFiles;

        @NotBlank
        @Lob
        private String content;
    }
}
