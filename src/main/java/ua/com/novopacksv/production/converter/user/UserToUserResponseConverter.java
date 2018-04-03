package ua.com.novopacksv.production.converter.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.user.UserResponse;
import ua.com.novopacksv.production.model.userModel.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserToUserResponseConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(User source) {
        List<String> roleNames = source.getRoles()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        UserResponse result = new UserResponse();
        result.setId(source.getId());
        result.setUserName(source.getUserName());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setRoleNames(roleNames);
        return result;
    }

}