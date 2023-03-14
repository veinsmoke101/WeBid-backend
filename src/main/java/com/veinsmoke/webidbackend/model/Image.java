package com.veinsmoke.webidbackend.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @ManyToOne( fetch = FetchType.LAZY )
    Auction auction;

    public Image(String name) {
        this.name = name;
    }

}
