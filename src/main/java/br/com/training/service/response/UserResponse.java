package br.com.training.service.response;

import br.com.training.model.User;

import java.time.LocalDate;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDate birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserResponse() {

    }

    public static User convertToDTO(User user) {
        return new User(user.getId(), user.getName(), user.getCpf(), user.getEmail(), user.getBirthDate());
    }
}
