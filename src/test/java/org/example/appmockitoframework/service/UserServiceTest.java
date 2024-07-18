package org.example.appmockitoframework.service;

import org.example.appmockitoframework.entity.User;
import org.example.appmockitoframework.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {


    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void before() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userService = new UserService(userRepository);
    }

    @Test
    void userNullCreateTest() {
        User user = null;

        assertThrows(NullPointerException.class, () -> {
            User saved = userService.save(user);
        });
    }

    @Test
    void emailAlreadyExistsTest() {
        User user = User.builder()
                .email("a@gmail.com")
                .build();
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new User()));
        assertThrows(RuntimeException.class, () -> {
            userService.save(user);
        });
    }


    @Test
    void usernameAlreadyExistTest() {
        User existingUser = User.builder()
                .email("b@gmail.com")
                .username("eshmat")
                .build();
        User newUser = User.builder()
                .email("a@gmail.com")
                .username("eshmat")
                .build();

        Mockito.when(userRepository.findByUsername("eshmat")).thenReturn(Optional.of(existingUser));
        assertThrows(RuntimeException.class, () -> {
            userService.save(newUser);
        });
    }


    @Test
    void save() {
        User user = User.builder()
                .email("a@gmail.com")
                .username("eshmat")
                .password("1234")
                .build();
        Mockito.when(userRepository.save(user)).thenReturn(User.builder()
                .email("a@gmail.com")
                .username("eshmat")
                .password("1234")
                .build());
        User saved = userService.save(user);
        Assertions.assertEquals(saved.getEmail(), user.getEmail());
    }

    @Test
    void get() {
        int id = 10;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(User.builder()
                .id(id)
                .build()));
        User user = userService.get(id);
        Assertions.assertEquals(user.getId(), id);
    }

    @Test
    void getOneUserNotFoundTest(){

        int id = 10;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,()->{
            userService.get(id);
        });

    }

    @Test
    void  getOneUserWithNullId(){
        Integer id= null;

        assertThrows(RuntimeException.class,()->{
            userService.get(id);
        });
    }
}