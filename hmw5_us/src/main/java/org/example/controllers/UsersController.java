package org.example.controllers;

import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.events.UserEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserRepository repository;
    private final UserEventPublisher publisher;

    public UsersController(UserRepository repository, UserEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @GetMapping("/")
    public List<User> listUsers() {
        return repository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = repository.save(user);
        publisher.publishUserCreated(savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = repository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            repository.save(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User deletedUser = repository.findById(id).orElse(null);
        if (deletedUser != null) {
            repository.delete(deletedUser);
            publisher.publishUserDeleted(deletedUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
