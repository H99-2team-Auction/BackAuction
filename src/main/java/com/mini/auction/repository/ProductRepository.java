package com.mini.auction.repository;

import com.mini.auction.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository // 빈등록 안해도 되나? -kang
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsSoldFalseOrderByModifiedAtDesc();
}

