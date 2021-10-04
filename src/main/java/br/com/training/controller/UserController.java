package br.com.training.controller;

import br.com.training.controller.dto.request.UserForm;
import br.com.training.controller.dto.response.UserResponse;
import br.com.training.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(produces = "application/json", consumes = "application/json")
	@Operation(summary = "Create user", responses = {
			@ApiResponse(description = "Create user success", responseCode = "201",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm user)  {
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
	@Operation(summary = "Get user", responses = {
			@ApiResponse(description = "Get fetched user", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
		return new ResponseEntity<>(userService.findByCpf(cpf), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", consumes = "application/json")
	@Operation(summary = "Get user", responses = {
			@ApiResponse(description = "Get all users", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponse> getAllUsers() {
		return userService.listAll();
	}

    @PutMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
	@Operation(summary = "Update user", responses = {
			@ApiResponse(description = "Update user", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserForm user, @PathVariable String cpf) {
		return new ResponseEntity<>(userService.update(user, cpf), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
	@Operation(summary = "Delete user", responses = {
			@ApiResponse(description = "Delete user", responseCode = "204",
					content = @Content),
			@ApiResponse(description = "User not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<Void> deleteUser(@PathVariable String cpf) {
		userService.deleteByCpf(cpf);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
