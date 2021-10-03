package br.com.training.service;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.exception.UserNotFoundException;
import br.com.training.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUser() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.empty());
        given(userRepository.save(userForm.toEntity())).willAnswer(invocation -> invocation.getArgument(0));

        UserResponse savedUser = userService.save(userForm);

        assertThat(savedUser).isNotNull();

        verify(userRepository).save(userForm.toEntity());
    }

    @Test
    void shouldNotRegisterUserWhenUserAlreadyExists() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.of(userForm.toEntity()));

        assertThrows(UserNotFoundException.class, () -> {
            userService.save(userForm);
        });

        verify(userRepository, never()).save(userForm.toEntity());
    }

    @Test
    void shouldReturnUserWhenTheyExist() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.of(userForm.toEntity()));

        UserResponse userFound = userService.findByCpf(userForm.getCpf());

        assertThat(userFound).isNotNull();
    }

    @Test
    void shouldReturnAExceptionWhenUserIsNotExistent() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findByCpf(userForm.getCpf());
        });

    }

    @Test
    void shouldReturnAllRegisteredUsers() {
        List<UserForm> users = new ArrayList<>();
        users.add(new UserForm("Alfredo", "alfredo@gmail.com", "40835104044", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        users.add(new UserForm("Pedrolino", "pedrolino@gmail.com", "82371582026", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        users.add(new UserForm("Astolfo", "astolfo@gmail.com", "09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        given(userRepository.findAll())
                .willReturn(users.stream().map(UserForm::toEntity)
                        .collect(Collectors.toList()));

        List<UserResponse> listOfUsers = userService.listAll();
        assertThat(listOfUsers).isNotNull();

    }

    @Test
    void shouldUpdateUserData() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.of(userForm.toEntity()));
        given(userRepository.save(userForm.toEntity())).willReturn(userForm.toEntity());

        final UserResponse updatedUser = userService.update(userForm, userForm.getCpf());

        assertThat(updatedUser).isNotNull();
        verify(userRepository).save(userForm.toEntity());

    }

    @Test
    void shouldReturnAExceptionWhenTryingToUpdateANonExistentUser() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.update(userForm, userForm.getCpf());
        });
    }

    @Test
    void shouldDeleteUser() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.of(userForm.toEntity()));
        userService.deleteByCpf(userForm.getCpf());

        verify(userRepository, atLeastOnce()).deleteByCpf(userForm.getCpf());
    }

    @Test
    void shouldThrowExceptionWhenTheresNoUserToBeDeleted() {
        final UserForm userForm = new UserForm("Astolfo","astolfo@gmail.com","09801097957", LocalDate.parse("1994-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        given(userRepository.findByCpf(userForm.getCpf())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteByCpf(userForm.getCpf());
        });
    }

}
