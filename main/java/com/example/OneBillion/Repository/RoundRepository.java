package com.example.OneBillion.Repository;

import com.example.OneBillion.Model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByStatusAndRoundCloseTimeBetween(String status, LocalDateTime start, LocalDateTime end);

}
