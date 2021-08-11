package br.com.training.service;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.service.request.UserForm;
import br.com.training.service.response.UserResponse;
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
        return userRepository.save(user.convertToObj()).convertToDTO();
    }

    @Transactional
    public UserResponse findByCpf(String cpf) {
        return userRepository.findByCpf(cpf).convertToDTO();
    }

    @Transactional
    public List<UserResponse> listAll() {
        List<User> newUser = userRepository.findAll();
        return newUser.stream()
                .map(User::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse update(UserForm user, String cpf) {
        User newUser = userRepository.findByCpf(cpf);
        newUser.setName(user.getName());
        newUser.setCpf(user.getCpf());
        newUser.setEmail(user.getEmail());
        newUser.setBirthDate(user.getBirthDate());
        return userRepository.save(newUser).convertToDTO();
    }

    @Transactional
    public void deleteByCpf(String cpf) {
        userRepository.deleteByCpf(cpf);
    }
}
