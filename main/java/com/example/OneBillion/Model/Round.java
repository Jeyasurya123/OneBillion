package com.example.OneBillion.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double roundAmount;

    private LocalDateTime roundCloseTime;

    private String formattedCloseTime;

    public String getFormattedCloseTime() {
        return formattedCloseTime;
    }

    public void setFormattedCloseTime(String formattedCloseTime) {
        this.formattedCloseTime = formattedCloseTime;
    }

    @Enumerated(EnumType.STRING)
    private RoundStatus status; // New field for status

    // Getters and Setters
    private String winningNumbers;

    public String getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(String winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRoundAmount() {
        return roundAmount;
    }

    public void setRoundAmount(Double roundAmount) {
        this.roundAmount = roundAmount;
    }

    public LocalDateTime getRoundCloseTime() {
        return roundCloseTime;
    }

    public void setRoundCloseTime(LocalDateTime roundCloseTime) {
        this.roundCloseTime = roundCloseTime;
    }

    public RoundStatus getStatus() {
        return status;
    }

    public void setStatus(RoundStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", roundAmount=" + roundAmount +
                ", roundCloseTime=" + roundCloseTime +
                ", status=" + status +
                ", winningNumbers='" + winningNumbers + '\'' +
                '}';
    }
}
