package com.example.splitwise.auth;

import com.example.splitwise.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestRequestService {

    private final ConversionService conversionService;

    @Autowired
    public RestRequestService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public UserDetails getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return getUserFrom(authentication).orElseThrow(() -> new IllegalArgumentException("No user found"));
    }

    private Optional<UserDetails> getUserFrom(Authentication authentication) {
        return Optional.ofNullable(((UserDetails) authentication.getPrincipal()));

    }

    private HttpHeaders httpHeaders() {

        String plainCredentials = getAuthenticationUser().getUsername()
            + ":" + SecurityContextHolder.getContext().getAuthentication().getCredentials();

        byte[] plainCredentialsBytes = plainCredentials.getBytes();
        byte[] encodedCredentialsBytes = Base64.encodeBase64(plainCredentialsBytes);
        String encodedCredentials = new String(encodedCredentialsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes());
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("Authorization", "Basic " + encodedCredentials);
        return headers;
    }

    private List<MediaType> acceptableMediaTypes() {

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        return acceptableMediaTypes;
    }

    public void userRestResponse() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(null, httpHeaders());
        ResponseEntity<User> responseEntity = restTemplate.exchange(
            "http://localhost:8082/api/account",
            HttpMethod.GET, request, User.class
        );
    }

    public User findUser(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(null, httpHeaders());
        ResponseEntity<User> responseEntity = restTemplate.exchange(
            "http://localhost:8082/api/account/" + id,
            HttpMethod.GET, request, User.class
        );
        return conversionService.convert(responseEntity.getBody(), User.class);
    }

    public HttpEntity<User> getUser(User user, HttpHeaders httpHeaders) {
        return new HttpEntity<>(user, httpHeaders);
    }
}
