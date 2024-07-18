package org.example.appmockitoframework.controller;

import lombok.RequiredArgsConstructor;
import org.example.appmockitoframework.entity.User;
import org.example.appmockitoframework.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public HttpEntity<?> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getOneUser(@PathVariable Integer id){

        return ResponseEntity.ok(userService.get(id));
    }
}
