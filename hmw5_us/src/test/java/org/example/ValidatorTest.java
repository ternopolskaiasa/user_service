package org.example;

import org.example.models.User;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    @Test
    public void testValidUser() {
        User user = new User();
        user.setFirstName("David");
        user.setLastName("Copperfield");
        user.setEmail("david.copperfield@example.com");

        assertDoesNotThrow(() -> validateUser(user));
    }

    @Test
    public void testInvalidEmail() {
        User user = new User();
        user.setFirstName("James");
        user.setLastName("Bond");
        user.setEmail("jamesbond");

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> validateUser(user));
        assertEquals("Invalid email format", exception.getMessage());
    }

    private void validateUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new InvalidParameterException("Invalid email format");
        }
    }
}
