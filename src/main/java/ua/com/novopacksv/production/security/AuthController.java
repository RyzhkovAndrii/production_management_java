package ua.com.novopacksv.production.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.user.UserResponse;
import ua.com.novopacksv.production.model.userModel.User;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    private final ModelConversionService conversionService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        return new ResponseEntity<>(securityService.login(username, password), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getLoggedInUser() {
        User user = securityService.getLoggedInUser();
        UserResponse response = conversionService.convert(user, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
