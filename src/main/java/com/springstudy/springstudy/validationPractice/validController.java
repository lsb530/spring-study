package com.springstudy.springstudy.validationPractice;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class validController {

    @PostMapping("api/user")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().build();
    }
}