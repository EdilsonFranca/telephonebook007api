package com.telephonebook.controller;

import com.telephonebook.dto.MessageDto;
import com.telephonebook.dto.UserDto;
import com.telephonebook.model.User;
import com.telephonebook.repository.UserRepository;
import com.telephonebook.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            String mensagem = "Já existe um usuário com o email informado.!";
            MessageDto error = new MessageDto( mensagem);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
