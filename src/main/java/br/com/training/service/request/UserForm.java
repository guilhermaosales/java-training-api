package br.com.training.service.request;

import br.com.training.model.User;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserForm {

    @NotEmpty
    @Size(min = 2, message = "user name should have at least {min} characters")
    private String name;

    @NotEmpty
    @Email
    private String email;

    @CPF
    private String cpf;
    private LocalDate birthDate;

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

    public User convertToObj() {
        return new User(name, email, cpf, birthDate);
    }

//    public isCpfValid(String cpf) {
//
//    }
}
