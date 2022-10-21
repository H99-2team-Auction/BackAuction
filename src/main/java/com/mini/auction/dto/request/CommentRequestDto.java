package com.mini.auction.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "내용을 입력하세요.")
    private String comment;
}
