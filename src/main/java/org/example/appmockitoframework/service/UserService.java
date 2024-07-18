package org.example.appmockitoframework.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.appmockitoframework.entity.User;
import org.example.appmockitoframework.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public User save(@NonNull User user) {


        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new RuntimeException(" email already  exist");
        }

        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isPresent()) {
            throw new RuntimeException("username already exist");
        }

        return userRepository.save(user);
    }

    public User get(Integer id) {
        if (id == null)
            throw new RuntimeException("id null");
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("hatolik bu id boyicha id  topilmadi")
        );
    }
}
