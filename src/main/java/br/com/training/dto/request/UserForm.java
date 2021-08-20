package br.com.training.dto.request;

import br.com.training.model.User;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class UserForm implements Serializable  {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "User name must not be blank")
    @Size(min = 3, max = 45, message = "User name should have at least {min} characters and at maximum {max}.")
    private String name;

    @NotBlank(message = "User email must not be blank.")
    @Email(message = "Email format invalid.")
    private String email;

    @NotBlank(message = "User CPF must not be blank.")
    @CPF(message = "Invalid CPF")
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

    public User convertToObj() {
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
