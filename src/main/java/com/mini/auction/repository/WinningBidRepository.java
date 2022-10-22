package com.mini.auction.repository;

import com.mini.auction.entity.WinningBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinningBidRepository extends JpaRepository<WinningBid, Long> {

}
