package io.spring.start.sample.hexagon.web.controllers;

import io.spring.start.sample.hexagon.core.dtos.User;
import io.spring.start.sample.hexagon.core.enums.UserStatus;
import io.spring.start.sample.hexagon.web.interfaces.requests.RegisterUserRequest;
import io.spring.start.sample.hexagon.web.interfaces.responses.BaseResponse;
import io.spring.start.sample.hexagon.web.usecases.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserUseCase userUseCase;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping(value = "/register",
            produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse<User>> registerUser (@RequestBody @NotNull RegisterUserRequest request) {
        // validate here (for target object include password)
        Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            return ResponseEntity.badRequest()
                    .body(BaseResponse.<User>builder()
                            .status("Failed")
                            .code("4000000")
                            .message("Invalid parameter")
                            .build());
        }
        // register
        User user = userUseCase.registerUser(
                request.getAccount(),
                request.getPassword(),
                request.getConfirmPassword()
        );
        // output log
        log.info("registered account: " + user.getId() + " " + user.getAccount());
        // set response
        return ResponseEntity.ok()
                .body(BaseResponse.<User>builder()
                        .status("Succeeded")
                        .code("0000000")
                        .message("SUCCESS")
                        .results(user)
                        .build());
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse<User>> getNewRegisteredUsers(
            @Valid @NotNull @PathVariable("id") Long id,
            @Valid @RequestParam(value = "status", required = false, defaultValue = "ACTIVATE") UserStatus status) {
        // get user
        User user = userUseCase.getUser(id, status).orElse(null);
        // set response
        return ResponseEntity.ok()
                .body(BaseResponse.<User>builder()
                        .status("Succeeded")
                        .code("0000000")
                        .message("SUCCESS")
                        .results(user)
                        .build());
    }

    @GetMapping(value = "/new-registered-users",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse<List<User>>> getNewRegisteredUsers() {
        // get users list
        List<User> users = userUseCase.getNewRegisteredUsers();
        // set response
        return ResponseEntity.ok()
                .body(BaseResponse.<List<User>>builder()
                        .status("Succeeded")
                        .code("0000000")
                        .message("SUCCESS")
                        .results(users)
                        .build());
    }

}
