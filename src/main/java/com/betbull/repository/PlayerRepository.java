package com.betbull.repository;

import com.betbull.model.GetPlayer;
import com.betbull.model.Player;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    void deletePlayerByPlayerId(Long id);
    Boolean existsByPlayerId(Long id);
    List<Player> findByTeam(String team);
    Boolean existsByTeam(String team);
}
