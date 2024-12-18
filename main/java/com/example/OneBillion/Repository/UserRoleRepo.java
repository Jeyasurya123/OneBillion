package com.example.OneBillion.Repository;

import com.example.OneBillion.Model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoles,Integer> {
}
