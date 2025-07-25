package com.chronosx.demochatbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.User;
import com.chronosx.demochatbe.enums.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByStatus(UserStatus status);

    List<User> findAllByUsernameContainingIgnoreCase(String username);

    List<User> findAllByUsernameIn(List<String> usernames);
}
