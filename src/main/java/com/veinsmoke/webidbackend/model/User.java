package com.veinsmoke.webidbackend.model;


import com.veinsmoke.webidbackend.model.superclass.SuperUser;
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
@Table(name = "_user")
public class User extends SuperUser {

    @Column(nullable = false)
    String name;

    String profileImg;

    @OneToMany( mappedBy = "author", fetch = FetchType.LAZY)
    List<Auction> auctionsCreated;

    @OneToMany( mappedBy = "buyer", fetch = FetchType.LAZY)
    List<Auction> auctionsBought;

    @OneToMany( mappedBy = "bidder", fetch = FetchType.LAZY)
    List<Bid> bids;

    @CreationTimestamp
    LocalDateTime createdAt;
    @CreationTimestamp
    LocalDateTime updatedAt;
}
