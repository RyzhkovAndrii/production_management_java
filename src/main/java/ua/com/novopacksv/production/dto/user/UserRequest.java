package ua.com.novopacksv.production.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private List<String> rolesNames; // todo put roles ???

}
