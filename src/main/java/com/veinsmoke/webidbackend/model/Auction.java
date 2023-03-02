package com.veinsmoke.webidbackend.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auction {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;

    @ManyToOne( fetch = FetchType.LAZY )
    User author;

    @ManyToOne( fetch = FetchType.LAZY )
    User buyer;

    @ManyToOne( fetch = FetchType.LAZY )
    Category category;

    @OneToMany( mappedBy = "auction", fetch = FetchType.LAZY)
    List<Bid> bids;

    @CreationTimestamp
    LocalDateTime createdAt;
    @CreationTimestamp
    LocalDateTime updatedAt;

}
