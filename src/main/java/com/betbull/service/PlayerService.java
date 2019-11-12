package com.betbull.service;

import com.betbull.exception.ErrorCodes;
import com.betbull.exception.PlayerException;
import com.betbull.model.*;
import com.betbull.repository.PlayerHistoryRepository;
import com.betbull.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerHistoryRepository playerHistoryRepository;

    public CreatePlayerResponse createPlayer(CreatePlayerRequest request){
        Player player  = new Player();
        CreatePlayerResponse createPlayerResponse = new CreatePlayerResponse();
        try {
            if(playerRepository.existsByPlayerId(request.getPlayerId())){
                throw new PlayerException("PlayerId already exist", ErrorCodes.PLAYER_ID_ALREADY_EXIST);
            }
            player.setPlayerId(request.getPlayerId());
            player.setPlayerName(request.getPlayerName());
            player.setStartDateOfContract(request.getStartDateOfContract());
            player.setTeam(request.getTeam());
            player.setEndDateOfContract(request.getEndDateOfContract());
            player.setPosition(request.getPosition());
            player.setDateOfBirth(request.getDateOfBirth());
            player.setCitizenship(request.getCitizenship());
            player.setFoot(request.getFoot());
            player.setCareerStartDate(request.getCareerStartDate());
            playerRepository.save(player);
            createPlayerResponse.setResultCode(0);
            createPlayerResponse.setResultDesc("Success");
        } catch (PlayerException e){
            createPlayerResponse.setResultCode(e.getErrorCode());
            createPlayerResponse.setResultDesc(e.getMessage());
            return createPlayerResponse;
        }catch (Exception e){
            createPlayerResponse.setResultCode(ErrorCodes.GENERAL_PLAYER_ERROR);
            createPlayerResponse.setResultDesc("GENERAL_ERROR");
        }
        return  createPlayerResponse;
    }


    public CreatePlayerHistoryResponse createPlayerHistory(CreatePlayerHistoryRequest request){
        PlayerHistory playerHistory  = new PlayerHistory();
        CreatePlayerHistoryResponse createPlayerHistoryResponse = new CreatePlayerHistoryResponse();
        try {
            playerHistory.setPlayerId(request.getPlayerId());
            playerHistory.setPlayerName(request.getPlayerName());
            playerHistory.setStartDateOfContract(request.getStartDateOfContract());
            playerHistory.setTeam(request.getTeam());
            playerHistory.setEndDateOfContract(request.getEndDateOfContract());
            playerHistoryRepository.save(playerHistory);
            createPlayerHistoryResponse.setResultCode(0);
            createPlayerHistoryResponse.setResultDesc("Success");
        } /*catch (PlayerException e){
            createPlayerHistoryResponse.setResultCode(e.getErrorCode());
            createPlayerHistoryResponse.setResultDesc(e.getMessage());
            return createPlayerHistoryResponse;
        }*/catch (Exception e){
            createPlayerHistoryResponse.setResultCode(ErrorCodes.GENERAL_PLAYER_ERROR);
            createPlayerHistoryResponse.setResultDesc("GENERAL_ERROR");
        }
        return  createPlayerHistoryResponse;
    }

    public GetPlayerListResponse getPlayerList(){

        List<Player> playerlist  = new ArrayList<>();
        GetPlayerListResponse getPlayerListResponse = new GetPlayerListResponse();
        try {

            playerlist = playerRepository.findAll();
            getPlayerListResponse.setPlayerList(playerlist);
            getPlayerListResponse.setResultCode(0);
            getPlayerListResponse.setResultDesc("Success");
        } /*catch (PlayerException e){
            createPlayerHistoryResponse.setResultCode(e.getErrorCode());
            createPlayerHistoryResponse.setResultDesc(e.getMessage());
            return createPlayerHistoryResponse;
        }*/catch (Exception e){
            getPlayerListResponse.setResultCode(ErrorCodes.GENERAL_PLAYER_ERROR);
            getPlayerListResponse.setResultDesc("GENERAL_ERROR");
        }
        return  getPlayerListResponse;
    }


    public GetTeamsOfSelectedPlayerResponse getTeamsOfSelectedPlayer(GetTeamsOfSelectedPlayerRequest request){
        PlayerHistory playerHistory  = new PlayerHistory();
        List<String> teamsOfSelectedPlayer = new ArrayList<>();
        GetTeamsOfSelectedPlayerResponse getTeamsOfSelectedPlayerResponse = new GetTeamsOfSelectedPlayerResponse();
        try {
           List<PlayerHistory> list = playerHistoryRepository.findByPlayerId(request.getPlayerId());
           if(list.isEmpty()){
               throw new PlayerException("PlayerId is not exist", ErrorCodes.PLAYER_ID_NOT_EXIST);
           }
           for(PlayerHistory ps : list){
               teamsOfSelectedPlayer.add(ps.getTeam());
           }
            getTeamsOfSelectedPlayerResponse.setTeamsOfSelectedPlayerList(teamsOfSelectedPlayer);
            getTeamsOfSelectedPlayerResponse.setResultCode(0);
            getTeamsOfSelectedPlayerResponse.setResultDesc("Success");
        } catch (PlayerException e){
            getTeamsOfSelectedPlayerResponse.setResultCode(e.getErrorCode());
            getTeamsOfSelectedPlayerResponse.setResultDesc(e.getMessage());
            return getTeamsOfSelectedPlayerResponse;
        }catch (Exception e){
            getTeamsOfSelectedPlayerResponse.setResultCode(ErrorCodes.GENERAL_PLAYER_ERROR);
            getTeamsOfSelectedPlayerResponse.setResultDesc("GENERAL_ERROR");
        }
        return  getTeamsOfSelectedPlayerResponse;
    }

    public GetPlayerListHasContractResponse getPlayerListHasContract(GetPlayerListHasContractRequest request){
        List<String> playerHasContract = new ArrayList<>();
        GetPlayerListHasContractResponse getPlayerListHasContractResponse = new GetPlayerListHasContractResponse();

        try {
            if(!playerRepository.existsByTeam(request.getTeamName().trim())){
                throw new PlayerException("Team does not exist", ErrorCodes.TEAM_NOT_EXIST);
            }
            List<Player> list = playerRepository.findByTeam(request.getTeamName().trim());
            for(Player player:list){
                if(player.getEndDateOfContract().compareTo(request.getYear()) > 0 ) {
                    playerHasContract.add(player.getPlayerName());
                }
            }
            getPlayerListHasContractResponse.setPlayerListHasContract(playerHasContract);
            getPlayerListHasContractResponse.setResultCode(0);
            getPlayerListHasContractResponse.setResultDesc("Success");
        } catch (PlayerException e){
            getPlayerListHasContractResponse.setResultCode(e.getErrorCode());
            getPlayerListHasContractResponse.setResultDesc(e.getMessage());
            return getPlayerListHasContractResponse;
        }catch (Exception e){
            getPlayerListHasContractResponse.setResultCode(ErrorCodes.GENERAL_PLAYER_ERROR);
            getPlayerListHasContractResponse.setResultDesc("GENERAL_ERROR");
        }
        return  getPlayerListHasContractResponse;
    }


    public GetPlayerSalaryInfoResponse getPlayerSalaryInfo(){

        RestTemplate restTemplate = new RestTemplate();

        String getTeamListUrl = "http://localhost:8081//api/v1/getteaminfolist";

        GetTeamInfoListResponse[] getTeamInfoListResponse = restTemplate.getForObject(getTeamListUrl , GetTeamInfoListResponse[].class);

        List<GetTeamInfoListResponse> getTeamInfoListResponseList = Arrays.asList(getTeamInfoListResponse);

        List<Player> playerlist  = new ArrayList<>();
        int expMonth ;
        int age;
        Double transferFee;
        Double teamCommission;
        Double contractPrice;

        GetPlayerSalaryInfoResponse getPlayerSalaryInfoResponse = new GetPlayerSalaryInfoResponse();
        List<GetPlayerSalary> playerSalaryInfo = new ArrayList<>();
           try {
            List<Player> playerList = playerRepository.findAll();
            for(Player player: playerList){
               GetPlayerSalary playerSalary = new GetPlayerSalary();
              age = findAge(player.getDateOfBirth());
               expMonth = findExpMonth(player.getCareerStartDate());
               transferFee = expMonth * (100.000 / age) ;
               teamCommission = transferFee / 10 ;
               contractPrice = transferFee + teamCommission ;

               playerSalary.setTransferFee(format(transferFee));
               playerSalary.setTeamCommission(format(teamCommission));
               playerSalary.setContractPrice(format(contractPrice));
               playerSalary.setFoot(player.getFoot());
               playerSalary.setPosition(player.getPosition());
               playerSalary.setPlayerName(player.getPlayerName());
               for(GetTeamInfoListResponse info: getTeamInfoListResponseList){
                    if(player.getTeamId().equals(info.getTeamId())){
                        playerSalary.setCurrency(info.getCurrency());
                    }
               }
               playerSalaryInfo.add(playerSalary);
            }

               getPlayerSalaryInfoResponse.setGetPlayerSalaryList(playerSalaryInfo);
               getPlayerSalaryInfoResponse.setResultCode(0);
               getPlayerSalaryInfoResponse.setResultDesc("Success");
        }catch (Exception e){
               getPlayerSalaryInfoResponse.setResultCode(ErrorCodes.GENERAL_PLAYER_ERROR);
               getPlayerSalaryInfoResponse.setResultDesc("GENERAL_ERROR");
        }
           return  getPlayerSalaryInfoResponse;
   }

    private int findAge(Date dateOfBirth){

        java.util.Date safeDateOfBirth = new Date(dateOfBirth.getTime());
        LocalDate birthDate = safeDateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(birthDate, today).getYears();
    }

    private int findExpMonth(Date carStartDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();

        String strToday = dateFormat.format(today);
        String carStartDAteStr = dateFormat.format(carStartDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate from = LocalDate.parse(carStartDAteStr, formatter);
        LocalDate to = LocalDate.parse(strToday, formatter);


       long expMonth = from.until(to, ChronoUnit.MONTHS);
       int exp = (int)  expMonth;

       return exp;
    }

    private double format(Double value){
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();

        return newInput ;
    }









}
