package com.example.splitwise.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginAuthenticationProvider(
        UserDetailsManager userDetailsManager,
        PasswordEncoder passwordEncoder
    ) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = userDetailsManager.loadUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            List<GrantedAuthority> authorities
                = new ArrayList<>();
            
            authorities.add(new SimpleGrantedAuthority(Authorities.USER.getName()));

            UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(
                user, password, authorities
            );

            return token;
        } else {
            throw new BadCredentialsException("Bad credentials, cant authenticate...");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
