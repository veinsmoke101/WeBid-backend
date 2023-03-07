package com.veinsmoke.webidbackend.model.superclass;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SuperUser {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    Long id;

    @Column( nullable = false, unique = true )
    String email;

    @Column(nullable = false)
    String password;

}
