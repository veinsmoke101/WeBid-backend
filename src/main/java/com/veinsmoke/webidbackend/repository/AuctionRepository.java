package com.veinsmoke.webidbackend.repository;

import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Category;
import com.veinsmoke.webidbackend.model.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByAuthor(Client author);
    List<Auction> findByTitle(String title);
    List<Auction> findByTitleAndAuthor(String title, Client author);


    @Query(value = "SELECT a FROM Auction a WHERE " +
            "(:title IS NULL OR lower(a.title) like lower(concat('%', :title, '%')) )" +
            "AND (:author IS NULL OR a.author = :author )" +
            "AND (:buyer IS NULL OR a.buyer = :buyer )" +
            "AND (:category IS NULL OR a.category = :category )" +
            "AND ( CAST(:startDate AS date) IS NULL OR a.startDate = :startDate )" +
            "AND (CAST(:endDate AS date) IS NULL OR a.endDate = :endDate )" +
            "AND (:startingPrice IS NULL OR a.startingPrice = :startingPrice )" +
            "AND (:buyNowPrice IS NULL OR a.buyNowPrice = :buyNowPrice )" +
            "AND (:minPrice IS NULL OR a.buyNowPrice >= :minPrice )" +
            "AND (:maxPrice IS NULL OR a.buyNowPrice <= :maxPrice )"
    )
    List<Auction> advancedSearch(
            @Param("title") String title,
            @Param("author") Client author,
            @Param("buyer") Client buyer,
            @Param("category") Category category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("startingPrice")Double startingPrice,
            @Param("buyNowPrice") Double buyNowPrice,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
