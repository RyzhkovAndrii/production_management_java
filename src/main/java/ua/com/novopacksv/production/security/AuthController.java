package ua.com.novopacksv.production.security;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.user.UserResponse;
import ua.com.novopacksv.production.model.userModel.User;
import ua.com.novopacksv.production.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    private final ModelConversionService conversionService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "username") String username,
                                        @RequestParam(name = "password") String password) {
        return new ResponseEntity<>(securityService.login(username, password), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getLoggedInUser() {
        User user = securityService.getLoggedInUser();
        UserResponse response = conversionService.convert(user, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestParam(name = "password")
            @Valid
            @NotBlank(message = "password is a required field")
            @Length(min = 6, message = "password must be at least 6 characters") String newPassword) {
        User currentUser = securityService.getLoggedInUser();
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userService.update(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
