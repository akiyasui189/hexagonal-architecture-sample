package io.spring.start.sample.hexagon.web.interfaces.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @NotNull
    @Email
    @Size(max = 256)
    private String account;
    @NotNull
    @Size(min = 8, max = 32)
    private String password;
    @NotNull
    @Size(min = 8, max = 32)
    private String confirmPassword;
}
