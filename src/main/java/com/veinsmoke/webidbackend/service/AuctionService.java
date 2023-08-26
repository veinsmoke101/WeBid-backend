package com.veinsmoke.webidbackend.service;

import com.veinsmoke.webidbackend.dto.AuctionRequest;
import com.veinsmoke.webidbackend.mapper.AuctionMapper;
import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Category;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ImageService imageService;
    private final AuctionMapper auctionMapper;


    @Transactional
    public void save(AuctionRequest auctionRequest, Client author, Category category)
    {

        Auction auction = auctionMapper.auctionRequestToAuction(auctionRequest);
        auction.setAuthor(author);
        auction.setCategory(category);

        auction.setImages(imageService.saveAll(auctionRequest.images(), auction));
        auctionRepository.save(auction);
    }

    public Optional<Auction> findById(Long id) {
        return auctionRepository.findById(id);
    }

    public List<Auction> findByAuthor(Client author) {
        return auctionRepository.findByAuthor(author);
    }

    public List<Auction> findAll() {
        return auctionRepository.findAll();
    }

    public List<Auction> findByTitle(String title) {
        return auctionRepository.findByTitle(title);
    }

    public List<Auction> findByTitleAndAuthor(String title, Client author) {
        return auctionRepository.findByTitleAndAuthor(title, author);
    }

    public List<Auction> search(
            int page,
            int size,
            String title,
            Client author,
            Client buyer,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Double startingPrice,
            Double buyNowPrice,
            Double minPrice,
            Double maxPrice
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return auctionRepository.advancedSearch(
                title,
                author,
                buyer,
                category,
                startDate,
                endDate,
                startingPrice,
                buyNowPrice,
                minPrice,
                maxPrice,
                pageable
        );
    }
}


