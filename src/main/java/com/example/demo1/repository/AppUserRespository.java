package com.example.demo1.repository;

import com.example.demo1.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRespository extends JpaRepository<AppUser,Integer> {
     Optional<AppUser> findByUsername(String username);
}
