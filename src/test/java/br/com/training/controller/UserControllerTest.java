package br.com.training.controller;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.model.User;
import br.com.training.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<UserForm> userFormList;

    @BeforeEach
    void setUp() {
        this.userFormList = new ArrayList<>();
        this.userFormList.add(new UserForm("Alfredo", "alfredo@gmail.com", "40835104044", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        this.userFormList.add(new UserForm("Pedrolino", "pedrolino@gmail.com", "82371582026", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        this.userFormList.add(new UserForm("Astolfo", "astolfo@gmail.com", "09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

    }
    @Test
    void shouldFetchUserByCPF() throws Exception {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userService.findByCpf(userForm.getCpf())).willReturn(new UserResponse(userForm.toEntity()));

        this.mockMvc.perform(get("/users/{cpf}", userForm.getCpf())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", equalTo(userForm.getName())))
                        .andExpect(jsonPath("$.email", is(userForm.getEmail())))
                        .andExpect(jsonPath("$.cpf", is(userForm.getCpf())))
                        .andExpect(jsonPath("$.birthDate", is(userForm.getBirthDate().toString())));
    }
    @Test
    void shouldUpdateUserByCPF() throws Exception {
        UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        when(userService.update(any(UserForm.class), anyString())).thenReturn(new UserResponse(userForm.toEntity()));

        mockMvc.perform(put("/users/{cpf}", userForm.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForm)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userForm.getName())))
                .andExpect(jsonPath("$.email", is(userForm.getEmail())))
                .andExpect(jsonPath("$.cpf", is(userForm.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(userForm.getBirthDate().toString())));
    }
    @Test
    void shouldDeleteUserByCPF() throws Exception {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userService.findByCpf(userForm.getCpf())).willReturn(new UserResponse(userForm.toEntity()));
        doNothing().when(userService).deleteByCpf(userForm.getCpf());

        this.mockMvc.perform(delete("/users/{cpf}", userForm.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
