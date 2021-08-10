package br.com.training.service;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public void deleteByCpf(String cpf) {
       userRepository.deleteByCpf(cpf);
    }
}
