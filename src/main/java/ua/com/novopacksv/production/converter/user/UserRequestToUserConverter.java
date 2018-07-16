package ua.com.novopacksv.production.converter.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.user.UserRequest;
import ua.com.novopacksv.production.model.userModel.Role;
import ua.com.novopacksv.production.model.userModel.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRequestToUserConverter implements Converter<UserRequest, User> {

    @Override
    public User convert(UserRequest source) {
        List<Role> roles = source.getRoles()
                .stream()
                .map(Role::valueOf)
                .collect(Collectors.toList());
        User result = new User();
        result.setUsername(source.getUsername());
        result.setPassword(source.getPassword());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setRoles(roles);
        return result;
    }

}