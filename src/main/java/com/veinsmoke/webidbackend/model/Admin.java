package com.veinsmoke.webidbackend.model;


import com.veinsmoke.webidbackend.model.superclass.SuperUser;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public class Admin extends SuperUser {
}
