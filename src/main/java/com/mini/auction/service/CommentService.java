package com.mini.auction.service;


import com.mini.auction.dto.request.CommentRequestDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.entity.Comment;
import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import com.mini.auction.exception.CommentExceptions.NotFoundCommentException;
import com.mini.auction.exception.ProductExceptions.NotFoundProductException;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CommentResponseDto createComment(Long productId,
                                            CommentRequestDto requestDto,
                                            Member member) {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);

        Comment comment = new Comment(requestDto.getComment(), member, product);

        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .comment(comment.getComment())
                .username(comment.getMember().getUsername())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }


    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsList(Long productId) {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // product 객체에 해당하는 댓글 리스트 불러오기
        List<Comment> commentList = commentRepository.findAllByProduct(product);

        // comment 객체를 response 로 옮겨서 리턴
        List<CommentResponseDto> responseList = new ArrayList<>();
        for (Comment comment : commentList) {
            responseList.add(new CommentResponseDto(comment));
        }

        return responseList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long productId, Long commentId,
                                            Member member,
                                            CommentRequestDto requestDto) {

        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        // 댓글 작성한 유저 맞는지 확인
        member.isAuthor(comment);
        // 댓글 객체 수정
        comment.update(requestDto);


        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto deleteComment(Long productId, Long commentId, Member member) {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);

        member.isAuthor(comment);

        commentRepository.delete(comment);

        return new CommentResponseDto(comment);
    }

}
