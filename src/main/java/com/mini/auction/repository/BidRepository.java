package com.mini.auction.repository;

import com.mini.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

//    Optional<User> findByUsername(String username);
}
