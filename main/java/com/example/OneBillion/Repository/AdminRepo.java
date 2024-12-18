package com.example.OneBillion.Repository;

import com.example.OneBillion.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Admin findByEmail(String name);
}
