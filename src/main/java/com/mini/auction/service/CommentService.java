package com.mini.auction.service;

import com.mini.auction.dto.request.CommentRequestDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.entity.Comment;
import com.mini.auction.exception.NotFoundCommentException;
import com.mini.auction.exception.NotFoundProductException;
import com.mini.auction.entity.Product;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CommentResponseDto createComment(Long productId,
                                            CommentRequestDto requestDto,
                                            Object user) throws NotFoundProductException {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);

        Comment comment = Comment.builder()
                .comment(requestDto.getComment())
                .user(user)
                .Product(product)
                .build();

        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .comment(comment.getComment())
                .username(user.getUsername())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }


    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsList(Long productId) throws NotFoundProductException {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // product 객체에 해당하는 댓글 리스트 불러오기
        List<Comment> commentList = commentRepository.findCommentsByProduct(product);

        // comment 객체를 response 로 옮겨서 리턴
        List<CommentResponseDto> responseList = new ArrayList<>();
        for (Comment comment : commentList) {
            responseList.add(new CommentResponseDto(comment));
        }

        return responseList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long productId, Long commentId,
                                            User user,
                                            CommentRequestDto requestDto) throws NotFoundProductException, NotFoundCommentException {

        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        // 댓글 작성한 유저 맞는지 확인
        user.checkUser(comment);
        // 댓글 객체 수정
        comment.update(requestDto);


        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto deleteComment(Long productId, Long commentId, User user)
            throws NotFoundCommentException, NotFoundProductException {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        // 댓글을 작성한 멤버인지 확인. userDetails 에서 불러온 유저객체와
        // comment repo 에서 불러온 comment 객체의 유저 객체 비교해서
        // 예외 터트리기
        user.checkUser(comment);

        commentRepository.delete(comment);

        return new CommentResponseDto(comment);
    }

}
