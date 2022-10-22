package com.mini.auction.controller;

import com.mini.auction.dto.ResponseDto;
import com.mini.auction.dto.request.CommentRequestDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.exception.CommentExceptions.NotFoundCommentException;
import com.mini.auction.service.CommentService;
import com.mini.auction.exception.ProductExceptions.NotFoundProductException;
import com.mini.auction.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/{productId}/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable Long productId,
                                           @RequestBody @Valid CommentRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws NotFoundProductException {

        CommentResponseDto responseDto = commentService.createComment(productId, requestDto, userDetails.getUser());
        return new ResponseEntity<>(ResponseDto.success(responseDto), setHeaders(), HttpStatus.OK);
    }

    /**
     * 댓글 조회
     */
    @GetMapping
    public ResponseEntity<?> getCommentsList(@PathVariable Long productId)
            throws NotFoundProductException {
        List<CommentResponseDto> responseDtos = commentService.getCommentsList(productId);
        return new ResponseEntity<>(ResponseDto.success(responseDtos), setHeaders(), HttpStatus.OK);
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long productId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody CommentRequestDto requestDto)
            throws NotFoundProductException, NotFoundCommentException {

        CommentResponseDto responseDto = commentService.updateComment(productId, commentId, userDetails.getUser(), requestDto);
        return new ResponseEntity<>(ResponseDto.success(responseDto), setHeaders(), HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long productId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws NotFoundCommentException, NotFoundProductException {
        CommentResponseDto responseDto = commentService.deleteComment(productId, commentId, userDetails.getUser());
        return new ResponseEntity<>(ResponseDto.success(responseDto), setHeaders(), HttpStatus.OK);
    }


    /**
     * 헤더 객체 생성 후 데이터 반환 형식 json 으로 설정
     */
    public HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return headers;
    }
}
