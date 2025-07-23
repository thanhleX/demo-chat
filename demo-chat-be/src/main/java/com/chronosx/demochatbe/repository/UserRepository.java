package com.chronosx.demochatbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {}
