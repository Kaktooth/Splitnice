package com.example.splitwise.auth;

import com.example.splitwise.controller.rest.UserAuthenticationRequest;
import com.example.splitwise.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestResponsesService {

    private final RestTemplate restTemplate;

    private final ConversionService conversionService;

    private final AuthenticationProvider authenticationProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RestResponsesService(RestTemplate restTemplate,
                                ConversionService conversionService,
                                AuthenticationProvider authenticationProvider,
                                PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.conversionService = conversionService;
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return getUserFrom(authentication).orElseThrow(() -> new IllegalArgumentException("No user found"));
    }

    private Optional<UserDetails> getUserFrom(Authentication authentication) {

        return Optional.ofNullable(((UserDetails) authentication.getPrincipal()));

    }

    HttpHeaders httpHeaders() {
        String plainCreds = getAuthenticationUser().getUsername()
            + ":" + "qweqwe";

        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes());
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }

    private List<MediaType> acceptableMediaTypes() {

        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        return acceptableMediaTypes;

    }

    public String getTokenForLoggedUser() {

        UserDetails user = getAuthenticationUser();
        HttpEntity<UserAuthenticationRequest>
            userAuthenticationRequestHttpEntity =
            new HttpEntity<>(
                new UserAuthenticationRequest(
                    user.getUsername(),
                    user.getPassword()
                ), httpHeaders());

        ResponseEntity<String> response = restTemplate
            .exchange(
                "http://localhost:8082/sign-in",
                HttpMethod.POST,
                userAuthenticationRequestHttpEntity,
                String.class);

        return response.getBody();
    }

    public void userRestResponse() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(new User(), httpHeaders());
        ResponseEntity<User> responseEntity = restTemplate.exchange(
            "http://localhost:8082/api/account",
            HttpMethod.GET, request, User.class
        );
    }

    public User findUser(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(new User(), httpHeaders());
        ResponseEntity<User> responseEntity = restTemplate.exchange(
            "http://localhost:8082/api/account/" + id,
            HttpMethod.GET, request, User.class
        );
        return conversionService.convert(responseEntity.getBody(), User.class);
    }

    HttpEntity<User> getUser(User user, HttpHeaders httpHeaders) {

        return new HttpEntity<>(user, httpHeaders);
    }
}
