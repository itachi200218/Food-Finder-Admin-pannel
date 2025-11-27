package com.example.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo1.model.FlaskUser;

public interface FlaskUserRepository extends JpaRepository<FlaskUser, Long> {
}

