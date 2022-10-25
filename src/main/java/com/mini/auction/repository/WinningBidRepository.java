package com.mini.auction.repository;

import com.mini.auction.domain.WinningBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinningBidRepository extends JpaRepository<WinningBid, Long> {

}
