package com.example.demo1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.repository.FlaskUserRepository;

@Service
public class AdminService {

    @Autowired
    private FlaskUserRepository flaskUserRepository;

    public long getFlaskUserCount() {
        return flaskUserRepository.count();
    }
}
