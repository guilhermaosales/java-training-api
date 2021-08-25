package br.com.training.controller.user;

import br.com.training.controller.user.dto.request.UserForm;
import br.com.training.controller.user.dto.response.UserResponse;
import br.com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> createUser(@RequestBody @Valid UserForm user)  {
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
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
	public ResponseEntity<?> deleteUser(@PathVariable String cpf) {
		userService.deleteByCpf(cpf);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
