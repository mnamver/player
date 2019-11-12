package com.betbull.controller;


import com.betbull.model.*;
import com.betbull.repository.PlayerHistoryRepository;
import com.betbull.repository.PlayerRepository;
import com.betbull.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerHistoryRepository playerHistoryRepository;

    @PostMapping("/createplayer")
    public CreatePlayerResponse createPlayerInfo(@Valid @RequestBody CreatePlayerRequest request){

        return playerService.createPlayer(request);
    }

    @PostMapping("/createplayerhistory")
    public CreatePlayerHistoryResponse createPlayerHistory(@Valid @RequestBody CreatePlayerHistoryRequest request){

        return playerService.createPlayerHistory(request);
    }


    @GetMapping("/getplayerlist")
    public GetPlayerListResponse getPlayerList(){

        return playerService.getPlayerList();
    }

    @PostMapping("/getteamsofselectedplayer")
    public GetTeamsOfSelectedPlayerResponse getTeamsOfSelectedPlayer(@Valid @RequestBody GetTeamsOfSelectedPlayerRequest request){

        return playerService.getTeamsOfSelectedPlayer(request);
    }

    @PostMapping("/getplayerlisthascontract")
    public GetPlayerListHasContractResponse getPlayerListHasContract(@Valid @RequestBody GetPlayerListHasContractRequest request){

        return playerService.getPlayerListHasContract(request);
    }

    @GetMapping("/getplayersalaryinfo")
    public GetPlayerSalaryInfoResponse getPlayerSalaryInfo(){

        return playerService.getPlayerSalaryInfo();
    }


    @Transactional
    @DeleteMapping("/deleteplayer")
    public void deletePlayerInfo(@PathVariable("id") Long id){

        playerRepository.deletePlayerByPlayerId(id);
    }


}
