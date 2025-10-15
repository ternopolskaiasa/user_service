package org.example;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.events.UserEventPublisher;
import org.example.models.User;
import org.example.models.UserEvent;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEventPublisherTest {

    @Mock
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @InjectMocks
    private UserEventPublisher userEventPublisher;

    @Test
    public void testPublishUserCreated() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setEmail("user@example.com");

        when(kafkaTemplate.send((ProducerRecord<String, UserEvent>) any())).thenAnswer(invocation -> {
            SendResult<String, UserEvent> result = mock(SendResult.class);
            RecordMetadata metadata = mock(RecordMetadata.class);
            when(metadata.partition()).thenReturn(0);
            when(result.getRecordMetadata()).thenReturn(metadata);
            ((ListenableFutureCallback<SendResult<String, UserEvent>>) invocation.getArguments()[1]).onSuccess(result);
            return result;
        });

        userEventPublisher.publishUserCreated(user);

        verify(kafkaTemplate, times(1)).send(eq("user-event-topic"), argThat(event ->
                event.getType().equals("CREATE") && event.getEmail().equals("user@example.com")));
    }

    @Test
    public void testPublishUserDeleted() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setEmail("user@example.com");

        when(kafkaTemplate.send((ProducerRecord<String, UserEvent>) any())).thenAnswer(invocation -> {
            SendResult<String, UserEvent> result = mock(SendResult.class);
            RecordMetadata metadata = mock(RecordMetadata.class);
            when(metadata.partition()).thenReturn(0);
            when(result.getRecordMetadata()).thenReturn(metadata);
            ((ListenableFutureCallback<SendResult<String, UserEvent>>) invocation.getArguments()[1]).onSuccess(result);
            return result;
        });

        userEventPublisher.publishUserDeleted(user);

        verify(kafkaTemplate, times(1)).send(eq("user-event-topic"), argThat(event ->
                event.getType().equals("DELETE") && event.getEmail().equals("user@example.com")));
    }

    @Test
    public void testPublishUserUpdated() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setEmail("user@example.com");

        when(kafkaTemplate.send((ProducerRecord<String, UserEvent>) any())).thenAnswer(invocation -> {
            SendResult<String, UserEvent> result = mock(SendResult.class);
            RecordMetadata metadata = mock(RecordMetadata.class);
            when(metadata.partition()).thenReturn(0);
            when(result.getRecordMetadata()).thenReturn(metadata);
            ((ListenableFutureCallback<SendResult<String, UserEvent>>) invocation.getArguments()[1]).onSuccess(result);
            return result;
        });

        userEventPublisher.publishUserUpdated(user);

        verify(kafkaTemplate, times(1)).send(eq("user-event-topic"), argThat(event ->
                event.getType().equals("UPDATE") && event.getEmail().equals("user@example.com")));
    }
}
