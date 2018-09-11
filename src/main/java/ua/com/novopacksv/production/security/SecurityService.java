package ua.com.novopacksv.production.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.userModel.User;
import ua.com.novopacksv.production.service.user.UserService;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UserService userService;

    public TokenResponse login(String username, String password) {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("User is not specified");
        }
        try {
            userService.findByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authToken);
            TokenResponse token = new TokenResponse();
            token.setAccessToken(jwtTokenService.createAccessToken(username));
            token.setRefreshToken(jwtTokenService.createRefreshToken(username));
            return token;
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("User does not exist");
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("Password is incorrect");
        }
    }

    public TokenResponse refreshToken(String refresh) {
        if (refresh == null) {
            throw new BadCredentialsException("Expired or invalid token");
        }
        jwtTokenService.checkToken(refresh);
        String username = jwtTokenService.getUsername(refresh);
        if (!StringUtils.hasText(username)) {
            throw new BadCredentialsException("Expired or invalid token");
        }
        try {
            userService.findByUsername(username);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("User does not exist");
        }
        TokenResponse token = new TokenResponse();
        token.setAccessToken(jwtTokenService.createAccessToken(username));
        token.setRefreshToken(jwtTokenService.createRefreshToken(username));
        return token;
    }

    public User getLoggedInUser() {
        String username = findLoggedInUsername();
        if (username == null) {
            throw new ResourceNotFoundException("There isn't logged in user");
        }
        return userService.findByUsername(username);
    }

    private String findLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails
                ? ((UserDetails) principal).getUsername()
                : null;
    }

}
