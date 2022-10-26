package com.mini.auction.service;


import com.mini.auction.dto.request.CommentRequestDto;
import com.mini.auction.dto.response.CommentResponseDto;
import com.mini.auction.domain.Comment;
import com.mini.auction.domain.Member;
import com.mini.auction.domain.Product;
import com.mini.auction.repository.CommentRepository;
import com.mini.auction.repository.ProductRepository;
import com.mini.auction.utils.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final Check check;

    @Transactional
    public CommentResponseDto createComment(Long productId,
                                            CommentRequestDto requestDto,
                                            Member member) {
        // 해당 게시물 없으면 예외 터트림
        check.isExistedProduct(productId);

        Comment comment = new Comment(requestDto.getComment(), member, productId);

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
        check.isExistedProduct(productId);
        // product 객체에 해당하는 댓글 리스트 불러오기
        List<Comment> commentList = commentRepository.findAllByProductId(productId);

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
        check.isExistedProduct(productId);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = check.isExistedComment(commentId);
        // 댓글 작성한 유저 맞는지 확인
        member.isAuthor(comment);
        // 댓글 객체 수정
        comment.update(requestDto);


        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto deleteComment(Long productId, Long commentId, Member member) {
        // 해당 게시물 없으면 예외 터트림
        check.isExistedProduct(productId);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = check.isExistedComment(commentId);
        // 댓글 작성한 유저 맞는지 확인
        member.isAuthor(comment);

        commentRepository.delete(comment);

        return new CommentResponseDto(comment);
    }

}
