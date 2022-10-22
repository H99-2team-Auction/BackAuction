package com.mini.auction.repository;

import com.mini.auction.entity.Comment;
import com.mini.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByProduct(Product product);

}
