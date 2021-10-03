package br.com.training.controller;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "API REST users")
@CrossOrigin( origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	@ApiOperation(value = "Create an user")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Returns created user"),
			@ApiResponse(code = 400, message = "Not found exception"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm user)  {
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@GetMapping (value = "/{cpf}")
	@ApiOperation(value = "Get an user by cpf")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns fetched user"),
			@ApiResponse(code = 400, message = "Not found exception"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
		return new ResponseEntity<>(userService.findByCpf(cpf), HttpStatus.OK);
    }

    @GetMapping
	@ApiOperation(value = "Get all registered users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns fetched users"),
			@ApiResponse(code = 400, message = "Not found exception"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponse> getAllUsers() {
		return userService.listAll();
	}

    @PutMapping(value = "/{cpf}")
	@ApiOperation(value = "Update an user by cpf")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns updated user data"),
			@ApiResponse(code = 400, message = "Not found exception"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserForm user, @PathVariable String cpf) {
		return new ResponseEntity<>(userService.update(user, cpf), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{cpf}")
	@ApiOperation(value = "Delete an user by cpf")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Successfull operation with no content"),
			@ApiResponse(code = 400, message = "Not found exception"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	public ResponseEntity<Void> deleteUser(@PathVariable String cpf) {
		userService.deleteByCpf(cpf);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
