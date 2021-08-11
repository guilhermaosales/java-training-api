package br.com.training.controller;

import br.com.training.model.User;
import br.com.training.service.UserService;
import br.com.training.service.request.UserForm;
import br.com.training.service.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RestControllerAdvice
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody @Valid UserForm user) {
		User preparedUser = userService.save(user.convertToObj());
		return new ResponseEntity<>(UserResponse.convertToDTO(preparedUser), HttpStatus.CREATED);
	}

	@GetMapping (value = "/{cpf}")
	public ResponseEntity<User> getUser(@PathVariable String cpf) {
		User foundUser = userService.findByCpf(cpf);
		return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> foundUsers = userService.listAll();
		return new ResponseEntity<>(foundUsers, HttpStatus.OK);
	}

    @PutMapping(value = "/{cpf}")
	public ResponseEntity<User> updateUser(@RequestBody @Valid UserForm user, @PathVariable String cpf) {
		User foundUser = userService.findByCpf(cpf);
		if (foundUser != null) {
			foundUser.setName(user.getName());
			foundUser.setCpf(user.getCpf());
			foundUser.setEmail(user.getEmail());
			foundUser.setBirthDate(user.getBirthDate());
			User newUser = userService.save(foundUser);
			return new ResponseEntity<>(UserResponse.convertToDTO(newUser), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/{cpf}")
	public ResponseEntity<Void> updateUser(@PathVariable String cpf) {
		User foundUser = userService.findByCpf(cpf);
		if (foundUser != null) {
			userService.deleteByCpf(cpf);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
