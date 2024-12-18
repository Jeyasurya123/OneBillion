package com.example.OneBillion.Repository;

import com.example.OneBillion.Model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Otp findByUserIdentifier(String userIdentifier);
}
