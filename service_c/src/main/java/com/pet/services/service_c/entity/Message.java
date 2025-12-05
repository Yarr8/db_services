package com.pet.services.service_c.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Key is required")
    @Column(nullable = false)
    private String key;

    @NotBlank(message = "Message is required")
    @Column(nullable = false)
    private String message;

}
