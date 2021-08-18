package br.com.training.controller;

import br.com.training.service.UserService;
import br.com.training.service.request.UserForm;
import br.com.training.service.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Validated
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm user)  {
		UserResponse preparedUser = userService.save(user);
		return new ResponseEntity<>(preparedUser, HttpStatus.CREATED);
	}

	@GetMapping (value = "/{cpf}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
		UserResponse foundUser = userService.findByCpf(cpf);
		if (foundUser != null) {
			return new ResponseEntity<>(foundUser, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponse> getAllUsers() {
		return userService.listAll();
	}

    @PutMapping(value = "/{cpf}")
	public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserForm user, @PathVariable String cpf) {
		UserResponse foundUser = userService.update(user, cpf);
		return new ResponseEntity<>(foundUser, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{cpf}")
	public ResponseEntity<Void> deleteUser(@PathVariable String cpf) {
		UserResponse foundUser = userService.findByCpf(cpf);
		if (foundUser != null) {
			userService.deleteByCpf(cpf);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}
