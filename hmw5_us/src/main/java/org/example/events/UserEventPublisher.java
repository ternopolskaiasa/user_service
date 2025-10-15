package org.example.events;

import org.example.models.User;
import org.example.models.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserEventPublisher(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserCreated(User user) {
        UserEvent event = new UserEvent("CREATE", user.getEmail());
        kafkaTemplate.send("user-event-topic", event);
    }

    public void publishUserDeleted(User user) {
        UserEvent event = new UserEvent("DELETE", user.getEmail());
        kafkaTemplate.send("user-event-topic", event);
    }

    public void publishUserUpdated(User user){
        UserEvent event = new UserEvent("UPDATE", user.getEmail());
        kafkaTemplate.send("user-event-topic", event);
    }
}
