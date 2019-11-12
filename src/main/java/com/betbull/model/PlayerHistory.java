package com.betbull.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "playerHistory")
public class PlayerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "team")
    private String team;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_Date_Of_Contract")
    private Date startDateOfContract;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_Date_Of_Contract")
    private Date endDateOfContract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getStartDateOfContract() {
        return startDateOfContract;
    }

    public void setStartDateOfContract(Date startDateOfContract) {
        this.startDateOfContract = startDateOfContract;
    }

    public Date getEndDateOfContract() {
        return endDateOfContract;
    }

    public void setEndDateOfContract(Date endDateOfContract) {
        this.endDateOfContract = endDateOfContract;
    }
}
