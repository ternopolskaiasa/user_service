package org.example;

import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Johnson");
        user.setEmail("alice.johnson@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("Alice", createdUser.getFirstName());
    }

    @Test
    public void testUpdateUser() {
        User originalUser = new User();
        originalUser.setId(1L);
        originalUser.setFirstName("Bob");
        originalUser.setLastName("Brown");
        originalUser.setEmail("bob.brown@example.com");

        User updatedUser = new User();
        updatedUser.setId(originalUser.getId());
        updatedUser.setFirstName("Robert");
        updatedUser.setLastName("Black");
        updatedUser.setEmail("robert.black@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(originalUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);
        assertNotNull(result);
        assertEquals("Robert", result.getFirstName());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Eve");
        user.setLastName("Green");
        user.setEmail("eve.green@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);
    }
}
