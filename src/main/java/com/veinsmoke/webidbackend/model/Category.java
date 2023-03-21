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
public class Category {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String name;

    @OneToMany( mappedBy = "category", fetch = FetchType.LAZY)
    List<Auction> auction;

    @ManyToOne( fetch = FetchType.LAZY)
    Category parent;

    @CreationTimestamp
    LocalDateTime createdAt;
    @CreationTimestamp
    LocalDateTime updatedAt;

}
