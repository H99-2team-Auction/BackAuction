package com.mini.auction.controller;


import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.CommentRequestDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.exception.CommentExceptions.NotFoundCommentException;
import com.mini.auction.exception.bidException.ProductExceptions.NotFoundProductException;
import com.mini.auction.security.user.UserDetailsImpl;
import com.mini.auction.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/product/{productId}/comment")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final MemberController memberController;

    /**
     * 댓글 등록
     */
    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable Long productId,
                                           @RequestBody @Valid CommentRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws NotFoundProductException {

        CommentResponseDto responseDto = commentService.createComment(productId, requestDto, userDetails.getMember());
        return new ResponseEntity<>(ResponseDto.success(responseDto), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 댓글 조회
     */
    @GetMapping
    public ResponseEntity<?> getCommentsList(@PathVariable Long productId)
            throws NotFoundProductException {
        List<CommentResponseDto> responseDtoList = commentService.getCommentsList(productId);
        return new ResponseEntity<>(ResponseDto.success(responseDtoList), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long productId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto = commentService.updateComment(productId, commentId, userDetails.getMember(), requestDto);
        return new ResponseEntity<>(ResponseDto.success(responseDto), memberController.setHeaders(), HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long productId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws NotFoundCommentException, NotFoundProductException {
        CommentResponseDto responseDto = commentService.deleteComment(productId, commentId, userDetails.getMember());
        return new ResponseEntity<>(ResponseDto.success(responseDto), memberController.setHeaders(), HttpStatus.OK);
    }


}
