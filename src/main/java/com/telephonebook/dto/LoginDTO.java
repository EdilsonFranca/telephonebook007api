package com.telephonebook.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank(message = "o Email não pode ser vazios")
    private String email;

    @NotBlank(message = "a Senha não pode ser vazios")
    private String password;
}
