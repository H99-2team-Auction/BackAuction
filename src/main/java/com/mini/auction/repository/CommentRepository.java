package com.mini.auction.repository;

import com.mini.auction.entity.Comment;
import com.mini.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment>  findCommentsByProduct(Product product);


}
