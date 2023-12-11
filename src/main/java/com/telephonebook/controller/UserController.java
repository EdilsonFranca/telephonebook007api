package com.telephonebook.controller;

import com.telephonebook.dto.UserDto;
import com.telephonebook.repository.UserRepository;
import com.telephonebook.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            userService.save(userDto);
            return ResponseEntity.ok("Usuario Cadastrado com sucesso!");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
