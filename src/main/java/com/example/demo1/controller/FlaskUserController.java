package com.example.demo1.controller;

import com.example.demo1.model.FlaskUser;
import com.example.demo1.repository.FlaskUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flask")
@CrossOrigin(origins = "*")
public class FlaskUserController {

    private final FlaskUserRepository flaskUserRepository;

    public FlaskUserController(FlaskUserRepository flaskUserRepository) {
        this.flaskUserRepository = flaskUserRepository;
    }

    @GetMapping("/users")
    public List<FlaskUser> getAllFlaskUsers() {
        return flaskUserRepository.findAll();
    }
}
