package org.example.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private String type;
    private String email;
}
