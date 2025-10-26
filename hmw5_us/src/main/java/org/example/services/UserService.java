package org.example.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.events.UserEventPublisher;
import org.example.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserEventPublisher userEventPublisher;
    private final UserValidator validator;

    public UserService(UserRepository userRepository, UserEventPublisher userEventPublisher, UserValidator validator) {
        this.userRepository = userRepository;
        this.userEventPublisher = userEventPublisher;
        this.validator = validator;
    }

    @CircuitBreaker(name = "userServiceCreate", fallbackMethod = "fallbackCreateUser")
    public User createUser(User user) {
        validator.validate(user);
        User savedUser = userRepository.save(user);
        userEventPublisher.publishUserCreated(savedUser);
        return savedUser;
    }

    @CircuitBreaker(name = "userServiceUpdate", fallbackMethod = "fallbackUpdateUser")
    public User updateUser(long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            throw new IllegalArgumentException("User's not found");
        }

        User existingUser = existingUserOpt.get();
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());

        validator.validate(existingUser);
        User updated = userRepository.save(existingUser);
        userEventPublisher.publishUserUpdated(updated);
        return updated;
    }

    @CircuitBreaker(name = "userServiceFind", fallbackMethod = "fallbackFindUserById")
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    @CircuitBreaker(name = "userServiceDelete", fallbackMethod = "fallbackDeleteUser")
    public void deleteUser(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User's not found");
        }

        User user = userOpt.get();
        userRepository.delete(user);
        userEventPublisher.publishUserDeleted(user);
    }
}
