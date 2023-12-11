package com.telephonebook.service;

import com.telephonebook.ValidacaoException;
import com.telephonebook.dto.UserDto;
import com.telephonebook.model.User;
import com.telephonebook.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public UserDto save(@Valid UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail()))
            throw new ValidacaoException("Já existe um usuário com o email informado.!");

        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        userRepository.save(user);

        return userDto;
    }
}
