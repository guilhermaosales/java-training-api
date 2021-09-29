package br.com.training.service;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.exception.UserNotFoundException;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse save(UserForm user) {
        if(userExists(user.getCpf())) {
            throw new UserNotFoundException("User already registered!");
        }
        return new UserResponse(userRepository.save(user.toEntity()));
    }

    @Transactional
    public UserResponse findByCpf(String cpf) {
        return new UserResponse(userRepository.findByCpf(cpf).orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Transactional
    public List<UserResponse> listAll() {
        List<User> newUser = userRepository.findAll();
        return newUser.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse update(UserForm user, String cpf) {
        User newUser = userRepository.findByCpf(cpf).orElseThrow(() -> new UserNotFoundException("User not found"));
        newUser.setId(user.toEntity().getId());
        newUser.setName(user.getName());
        newUser.setCpf(user.getCpf());
        newUser.setEmail(user.getEmail());
        newUser.setBirthDate(user.getBirthDate());
        return new UserResponse(userRepository.save(newUser));
    }

    @Transactional
    public void deleteByCpf(String cpf) {
        findByCpf(cpf);
        userRepository.deleteByCpf(cpf);
    }

    public boolean userExists(String cpf) {
        return userRepository.findByCpf(cpf).isPresent();
    }
}
