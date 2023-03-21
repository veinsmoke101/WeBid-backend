package com.veinsmoke.webidbackend.service;

import com.veinsmoke.webidbackend.dto.AuctionRequest;
import com.veinsmoke.webidbackend.mapper.AuctionMapper;
import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Category;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<Auction> find(Long id) {
        return auctionRepository.findById(id);
    }
}


