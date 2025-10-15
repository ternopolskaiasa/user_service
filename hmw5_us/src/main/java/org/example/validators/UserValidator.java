package org.example.validators;

import org.example.models.User;
import org.example.models.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserValidator(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void validate(User user) {
        if (user == null || user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("All fields need to be filed");
        }

        if (user.getFirstName().length() > 50 || user.getLastName().length() > 50) {
            throw new IllegalArgumentException("Name and surname need to be equal or less then 50 symbols");
        }
    }

    public void publishUserCreated(User user) {
        UserEvent event = new UserEvent("CREATE", user.getEmail());
        kafkaTemplate.send("user-event-topic", event);
    }

    public void publishUserDeleted(User user) {
        UserEvent event = new UserEvent("DELETE", user.getEmail());
        kafkaTemplate.send("user-event-topic", event);
    }

    public void publishUserUpdated(User user) {
        UserEvent event = new UserEvent("UPDATE", user.getEmail());
        kafkaTemplate.send("user-event-topic", event);
    }
}
