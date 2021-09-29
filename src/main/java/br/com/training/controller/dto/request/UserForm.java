package br.com.training.controller.dto.request;

import br.com.training.controller.dto.response.UserResponse;
import br.com.training.model.User;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class UserForm implements Serializable  {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 3, max = 45, message = "user name should have at least {min} characters and at maximum {max}.")
    private String name;

    @NotBlank
    @Email(message = "email must be filled correctly.")
    private String email;

    @NotBlank
    @CPF
    private String cpf;
    private LocalDate birthDate;

    public UserForm() {
        // NTD
    }

    public UserForm(String name, String email, String cpf, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public User toEntity() {
        return new User(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
