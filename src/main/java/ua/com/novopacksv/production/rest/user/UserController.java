package ua.com.novopacksv.production.rest.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.user.UserRequest;
import ua.com.novopacksv.production.dto.user.UserResponse;
import ua.com.novopacksv.production.model.userModel.User;
import ua.com.novopacksv.production.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelConversionService conversionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<User> users = userService.findAll();
        List<UserResponse> response = conversionService.convert(users, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponse response = conversionService.convert(user, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserRequest request) {
        User user = conversionService.convert(request, User.class);
        user = userService.save(user);
        UserResponse response = conversionService.convert(user, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        User user = conversionService.convert(request, User.class);
        user.setId(id);
        user = userService.update(user);
        UserResponse response = conversionService.convert(user, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}