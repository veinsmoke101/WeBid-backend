package com.veinsmoke.webidbackend.controller;

import com.veinsmoke.webidbackend.dto.AuctionRequest;
import com.veinsmoke.webidbackend.dto.AuctionResponse;
import com.veinsmoke.webidbackend.mapper.AuctionMapper;
import com.veinsmoke.webidbackend.mapper.ImageMapper;
import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Category;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.service.AuctionService;
import com.veinsmoke.webidbackend.service.CategoryService;
import com.veinsmoke.webidbackend.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final ClientService clientService;
    private final CategoryService categoryService;
    private final ImageMapper imageMapper;
    private final AuctionMapper auctionMapper;

    @PostMapping
    public ResponseEntity<String> saveAuction(@Valid @ModelAttribute AuctionRequest auctionRequest, Authentication authentication) {

        if(auctionRequest.images().size() > 10)
            return ResponseEntity.badRequest().body("Maximum 5 images are allowed");

        if(auctionRequest.buyNowPrice() < auctionRequest.startingPrice())
            return ResponseEntity.badRequest().body("Buy now price must be greater than starting price");

        Client client = clientService.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Category category = categoryService.findByName(auctionRequest.category())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));


        auctionService.save(auctionRequest, client, category);

        return ResponseEntity.ok("Auction saved");
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public ResponseEntity<AuctionResponse> find(@PathVariable("id") final Long id) {
        Auction auction = auctionService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found"));


        return ResponseEntity.ok(auctionMapper.auctionToAuctionResponse(auction));
    }



}
