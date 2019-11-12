package com.betbull.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = " player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_Id")
    private Long playerId;

    @Column(name = "player_name")
    private String playerName;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date_of_contract")
    private Date startDateOfContract;

    @Column(name = "team")
    private String team;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date_of_contract")
    private Date endDateOfContract;

    @Column(name = "position")
    private String position;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "foot")
    private String foot;

    @Temporal(TemporalType.DATE)
    @Column(name ="career_start_date")
    private Date careerStartDate;

    @Column(name ="team_id")
    private Long teamId;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCareerStartDate() {
        return careerStartDate;
    }

    public void setCareerStartDate(Date careerStartDate) {
        this.careerStartDate = careerStartDate;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
