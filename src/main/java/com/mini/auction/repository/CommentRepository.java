package com.mini.auction.repository;

import com.mini.auction.domain.Comment;
import com.mini.auction.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByProduct(Product product);
    void deleteAllByProduct(Product product);

}
