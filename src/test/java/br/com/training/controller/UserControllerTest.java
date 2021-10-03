package br.com.training.controller;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.exception.UserExceptionResponse;
import br.com.training.exception.UserNotFoundException;
import br.com.training.model.User;
import br.com.training.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private List<User> user;

    @BeforeEach
    void setUp() {
        this.user = new ArrayList<>();
        this.user.add(new User(1L, "Alfredo", "alfredo@gmail.com", "40835104044", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        this.user.add(new User(2L, "Pedrolino", "pedrolino@gmail.com", "82371582026", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        this.user.add(new User(3L, "Astolfo", "astolfo@gmail.com", "09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

    }
    @Test
    void shouldFetchAllUsers() throws Exception {
        given(userService.listAll()).willReturn(user.stream().map(UserResponse::new).collect(Collectors.toList()));
        this.mockMvc.perform(get("/users/")).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(user.size())));

    }

    @Test
    void shouldFetchEmptyListWhenNoUserIsRegistered() throws Exception {
        when(userService.listAll()).thenReturn(Collections.EMPTY_LIST);
        this.mockMvc.perform(get("/users/")).andExpect(status().isOk()).andExpect(content().string("[]"));

    }
    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        when(userService.save(any(UserForm.class))).thenReturn(new UserResponse(userForm.toEntity()));

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForm)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(userForm.getName())))
                .andExpect(jsonPath("$.email", is(userForm.getEmail())))
                .andExpect(jsonPath("$.cpf", is(userForm.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(userForm.getBirthDate().toString())));
    }
    @Test
    void shouldNotCreateUserAndThrowExceptionIfAlreadyExists() throws Exception {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        when(userService.save(any(UserForm.class))).thenThrow(UserNotFoundException.class);

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
