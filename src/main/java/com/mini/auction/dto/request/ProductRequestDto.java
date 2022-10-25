package com.mini.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

public class ProductRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductRequestPostDto {

        @NotBlank
        private String title;

        private Integer lowPrice;

        @NotBlank
        @Lob
        private String content;
    }
}
