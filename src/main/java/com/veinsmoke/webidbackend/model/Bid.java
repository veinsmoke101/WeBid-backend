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
public class Bid {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne( fetch = FetchType.LAZY )
    Auction auction;

    @ManyToOne( fetch = FetchType.LAZY )
    User bidder;

    @Column( nullable = false)
    Long bidPrice;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
