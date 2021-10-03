package br.com.training.controller;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm user)  {
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@GetMapping (value = "/{cpf}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
		return new ResponseEntity<>(userService.findByCpf(cpf), HttpStatus.OK);
    }

    @GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponse> getAllUsers() {
		return userService.listAll();
	}

    @PutMapping(value = "/{cpf}")
	public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserForm user, @PathVariable String cpf) {
		return new ResponseEntity<>(userService.update(user, cpf), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{cpf}")
	public ResponseEntity<Void> deleteUser(@PathVariable String cpf) {
		userService.deleteByCpf(cpf);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
