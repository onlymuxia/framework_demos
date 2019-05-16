package com.onlymuxia.demos.jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.onlymuxia.demos.jwt.entrys.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}