package com.veinsmoke.webidbackend.model.superclass;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuperUser {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    Long id;

    @Column( nullable = false, unique = true )
    String email;

    @Column(nullable = false)
    String password;

}
