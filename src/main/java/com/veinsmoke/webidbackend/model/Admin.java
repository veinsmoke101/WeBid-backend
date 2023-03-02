package com.veinsmoke.webidbackend.model;


import com.veinsmoke.webidbackend.model.superclass.SuperUser;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
public class Admin extends SuperUser {
}
