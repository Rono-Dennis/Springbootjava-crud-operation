package com.meeting.planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    User getById(int id);
}
