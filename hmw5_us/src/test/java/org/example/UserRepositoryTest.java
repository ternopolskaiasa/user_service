package org.example;

import org.example.models.User;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findById(user.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("John", retrievedUser.get().getFirstName());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");

        userRepository.save(user);
        userRepository.delete(user);

        Optional<User> retrievedUser = userRepository.findById(user.getId());
        assertFalse(retrievedUser.isPresent());
    }
}
