package com.example.splitwise.controller;

import com.example.splitwise.model.User;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.expense.IndividualExpense;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    private RequestCallback requestCallback(final User updatedInstance) {
        return clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), updatedInstance);

            String plainCredentials = getAuthenticationUser().getUsername()
                + ":" + SecurityContextHolder.getContext().getAuthentication().getCredentials();

            byte[] plainCredentialsBytes = plainCredentials.getBytes();
            byte[] encodedCredentialsBytes = Base64.encodeBase64(plainCredentialsBytes);
            String encodedCredentials = new String(encodedCredentialsBytes);

            clientHttpRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            clientHttpRequest.getHeaders().setAccept(acceptableMediaTypes());
            clientHttpRequest.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");

            clientHttpRequest.getHeaders().add("Authorization", "Basic " + encodedCredentials);

        };
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
        HttpEntity<User> request = new HttpEntity<>(
            null
            , httpHeaders());

        ResponseEntity<User> responseEntity = restTemplate.exchange(
            "http://localhost:8082/api/account/" + id,
            HttpMethod.GET, request, User.class
        );

        return conversionService.convert(responseEntity.getBody(), User.class);
    }

    public User editUser(Integer id, User user,
                         String edit, String parameter) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Integer> params = new HashMap<>();
        params.put("id", 1);

        User updatedUser = new User(1, "updated@gmail.com", "045345345", "fwef235", true);

        if (Objects.equals(edit, "email")) {
            updatedUser.setEmail(parameter);
        } else if (Objects.equals(edit, "password")) {
            updatedUser.setPassword(parameter);
        } else {
            updatedUser.setPhone(parameter);
        }

        ResponseEntity<User> responseUpdate = restTemplate.execute(
            "http://localhost:8082/api/account/" + id,
//                + "?edit=" + edit,//+"&"+parameter,
            HttpMethod.PUT, requestCallback(updatedUser),
            clientHttpResponse -> null
        );
        HttpEntity<User> request = new HttpEntity<>(
            null
            , httpHeaders());

        ResponseEntity<User> responseEntity = restTemplate.exchange(
            "http://localhost:8082/api/account/" + id,
            HttpMethod.GET, request, User.class
        );
        return conversionService.convert(responseEntity.getBody(), User.class);
    }

    public ExpenseDto createExpense(ExpenseDto expense) {
        RestTemplate restTemplate = new RestTemplate();
//        ExpenseDto newExpense = conversionService.convert(expense, ExpenseDto.class);

        ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

        String test;
        try {
            test = objectMapper.writeValueAsString(expense);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<ExpenseDto> request = new HttpEntity<>(
            expense,
            httpHeaders());

        ResponseEntity<ExpenseDto> Entity = restTemplate.exchange(
            "http://localhost:8082/api/add-expense", HttpMethod.POST,
            request, ExpenseDto.class
        );

        return conversionService.convert(Entity.getBody(), ExpenseDto.class);
    }

    public HttpEntity<User> getUser(User user, HttpHeaders httpHeaders) {
        return new HttpEntity<>(user, httpHeaders);
    }
}
