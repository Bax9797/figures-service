package com.test.figures.users;

import com.test.figures.entity.user.User;
import com.test.figures.repository.users.UserRepository;
import com.test.figures.service.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldLoadUserByUsername() {
        User user = new User().setUsername("name");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        UserDetails given = userService.loadUserByUsername("name");
        assertEquals(user.getUsername(), given.getUsername());
    }
}