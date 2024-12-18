package com.example.OneBillion.Service;

import com.example.OneBillion.Model.Round;
import com.example.OneBillion.Model.RoundStatus;
import com.example.OneBillion.Repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoundService {

    @Autowired
    private RoundRepository roundRepository;

    public Round createRound(Double roundAmount, LocalDateTime roundCloseTime, RoundStatus status) {
        Round round = new Round();
        round.setRoundAmount(roundAmount);
        round.setRoundCloseTime(roundCloseTime);
        round.setStatus(status);
        return roundRepository.save(round);
    }


    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }
    public Round getRoundById(Long id) {
        return roundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Round with ID " + id + " not found"));
    }

    // Method to save a round
    public void save(Round round) {
        roundRepository.save(round);
    }
    public Round updateRoundStatus(Long roundId, RoundStatus status) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new RuntimeException("Round not found"));
        round.setStatus(status);
        return roundRepository.save(round);
    }

//user

//    public List<Round> getAllRounds1(){
//        List<Round> rounds = roundRepository.findAll();
//
//        // Dynamically update the status of each round based on current time
//        for (Round round : rounds) {
//            if (round.getRoundCloseTime().isAfter(LocalDateTime.now())) {
//                round.setStatus(RoundStatus.valueOf("UPCOMING"));
//            } else if (round.getRoundCloseTime().isBefore(LocalDateTime.now()) && round.getRoundCloseTime().plusHours(1).isAfter(LocalDateTime.now())) {
//                round.setStatus(RoundStatus.valueOf("ACTIVE"));
//            } else {
//                round.setStatus(RoundStatus.valueOf("CLOSED"));
//            }
//        }
//
//        return rounds;
//    }

    public boolean participateInRound(Long roundId, Long userId) {
        Round round = roundRepository.findById(roundId).orElse(null);

        if (round != null && "ACTIVE".equals(round.getStatus())) {
            // Participation logic here (e.g., save to the database)
            return true;
        }

        return false; // Round is not active
    }

}
