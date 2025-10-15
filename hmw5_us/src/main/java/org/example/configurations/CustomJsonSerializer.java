package org.example.configurations;

import org.example.models.UserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomJsonSerializer implements Serializer<UserEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public byte[] serialize(String s, UserEvent userEvent) {
        try {
            return objectMapper.writeValueAsBytes(userEvent);
        } catch (Exception e) {
            throw new SerializationException("Error in serialization", e);
        }
    }

    @Override
    public void close() {}
}
