package com.mohit.authservice.userrepository;

import com.mohit.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u = :user")
    void updatePassword(User user, String password);
}
