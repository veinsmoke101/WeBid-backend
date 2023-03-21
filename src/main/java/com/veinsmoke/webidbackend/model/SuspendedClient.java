package com.veinsmoke.webidbackend.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuspendedClient  {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;

    @Column( nullable = false )
    String reason;

    @Column( nullable = false )
    LocalDateTime suspendedUntil;

    @OneToOne( fetch = FetchType.LAZY )
    Client client;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

}

