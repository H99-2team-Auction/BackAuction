package com.mini.auction.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public ResponseEntity<?> createComment(Long productId, String text, Object user) throws NotFoundProductException {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);

        Comment comment = Comment.builder()
                .comment(text)
                .user(user)
                .Product(product)
                .build();

        commentRepository.save(comment);

        return new ResponseEntity<>(CommentResponse.builder()
                .comment(comment.getComment())
                .username(user.getUsername())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build());
    }


    public ResponseEntity<?> getCommentsList(Long productId) {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // product 객체에 해당하는 댓글 리스트 불러오기
        List<Comment> commentList = commentRepository.findCommentsByProduct(product);

        // comment 객체를 response 로 옮겨서 리턴
        List<CommentResponse> responseList = new ArrayList<>();
        for (Comment comment : commentList) {
            responseList.add(new CommentResponse(comment));
        }

        return new ResponseEntity<>(responseList);
    }

    public ResponseEntity<?> deleteComment(Long productId, Long commentId, Object user) throws NotFoundCommentException {
        // 해당 게시물 없으면 예외 터트림
        Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
        // 해당 댓글 없으면 예외 터트림
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        // 댓글을 작성한 멤버인지 확인. userDetails 에서 불러온 유저객체와
        // comment repo 에서 불러온 comment 객체의 유저 객체 비교해서
        // 예외 터트리기
        user.checkAuthor(comment);

        commentRepository.delete(comment);

        return null;
    }
}
