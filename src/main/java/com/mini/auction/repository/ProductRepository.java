package com.mini.auction.repository;

import com.mini.auction.entity.Member;
import com.mini.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository // 빈등록 안해도 되나? -kang
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsSoldFalseOrderByModifiedAtDesc();
}

