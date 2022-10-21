package com.mini.auction.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private String comment;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CommentResponse(Comment comment) {
        this.comment = comment.getComment();
//        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
