package br.com.training.controller.dto.response;

import br.com.training.model.User;
//import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(
//            value = "User name",
//            example = "Ragnar Lothbrok")
    @NotBlank
    @Size(min = 3, max = 45, message = "user name should have at least {min} characters and at maximum {max}.")
    private String name;

//    @ApiModelProperty(
//            value = "User email",
//            example = "ragnar@vikings.com")
    @NotBlank
    @Email(regexp=".*@.*\\..*", message = "email must be filled correctly.")
    private String email;

//    @ApiModelProperty(
//            value = "User cpf",
//            example = "12345678910",
//            notes = "Portuguese for Natural Persons Register" )
    @NotBlank
    @CPF
    private String cpf;

//    @ApiModelProperty(
//            value = "User birthdate",
//            example = "21-12-1890")
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
