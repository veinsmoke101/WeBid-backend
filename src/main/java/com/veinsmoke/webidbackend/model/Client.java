package com.veinsmoke.webidbackend.model;


import com.veinsmoke.webidbackend.model.superclass.SuperUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Client extends SuperUser {

    @Column(nullable = false)
    String name;

    String profileImg;

    @Column(nullable = false)
    String verificationCode;

    LocalDateTime verificationTokenExpireAt;

    @Column( columnDefinition = "boolean default false")
    Boolean verified;

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

    @PrePersist
    public void prePersist() {
        this.verified = false;
    }
}
