package br.com.training.controller.user.dto.response;

import br.com.training.model.User;

import java.io.Serializable;
import java.time.LocalDate;

public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String cpf;
    private LocalDate birthDate;

    public UserResponse() {
        // NTD
    }

    public UserResponse(String name, String email, String cpf, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public UserResponse(User user) {
        name = user.getName();
        email = user.getEmail();
        cpf =  user.getCpf();
        birthDate = user.getBirthDate();
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
