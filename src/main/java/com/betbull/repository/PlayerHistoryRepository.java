package com.betbull.repository;

import com.betbull.model.PlayerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerHistoryRepository extends JpaRepository<PlayerHistory, Long> {

    public List<PlayerHistory> findByPlayerId(Long playerId);
    boolean existsByPlayerId(Long playerId);
}
