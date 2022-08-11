package com.vishnevskiy315.service;

import com.vishnevskiy315.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class UserServiceImpl {
    String URL = "http://94.198.50.185:7081/api/users";

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    String finalResult;


    public ResponseEntity<String> getAllUsers() {
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-Type", "application/json");
        String cookieArray = responseEntity.getHeaders().getFirst("set-cookie");
        String cookie = Arrays.stream(cookieArray.split(";"))
                .findFirst()
                .map(Object::toString)
                .orElse("");
        headers.add("Cookie", cookie);
        System.out.println();
        return responseEntity;
    }

    public void addUser(User user, String sessionId) {
        headers.add("Cookie", sessionId);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        String result = restTemplate.postForObject(URL, requestEntity, String.class);
        System.out.println("result 1: " + result);
        finalResult = result;
        System.out.println();
    }


    public void editUser(User user, String sessionId) {
        headers.add("Cookie", sessionId);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
        System.out.println("result 2: " + responseEntity.getBody());
        finalResult = finalResult + responseEntity.getBody();
        System.out.println();
    }


    public void deleteUser(User user, String sessionId) {
        headers.add("Cookie", sessionId);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + user.getId(), HttpMethod.DELETE, requestEntity, String.class);
        System.out.println("result 3: " + responseEntity.getBody());
        finalResult = finalResult + responseEntity.getBody();
        System.out.println(finalResult);
    }
}
