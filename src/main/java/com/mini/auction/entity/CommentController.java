package com.mini.auction.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/product/{productId}/comment")
    public ResponseEntity<?> createComment(@PathVariable Long productId,
                                           @RequestBody String text,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotFoundProductException {
        return commentService.createComment(productId, text, userDetails.getUser());
    }

    @GetMapping("/product/{productId}/comment")
    public ResponseEntity<?> getCommentsList(@PathVariable Long productId) {
        return commentService.getCommentsList(productId);
    }

    @DeleteMapping("/product/{productId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long productId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(productId, commentId, userDetails.getUser());
    }
}
