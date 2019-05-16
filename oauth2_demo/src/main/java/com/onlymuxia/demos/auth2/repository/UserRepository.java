package com.onlymuxia.demos.auth2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.onlymuxia.demos.auth2.entrys.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}