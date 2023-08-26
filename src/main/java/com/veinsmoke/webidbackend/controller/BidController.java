package com.veinsmoke.webidbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.veinsmoke.webidbackend.dto.BidRequest;
import com.veinsmoke.webidbackend.dto.BidResponse;
import com.veinsmoke.webidbackend.mapper.BidMapper;
import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Bid;
import com.veinsmoke.webidbackend.model.Category;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.service.AuctionService;
import com.veinsmoke.webidbackend.service.BidService;
import com.veinsmoke.webidbackend.service.ClientService;
import io.ably.lib.rest.AblyRest;
import io.ably.lib.types.AblyException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
@Slf4j
public class BidController {

    private final AblyRest ablyRest;
    private final BidService bidService;
    private final AuctionService auctionService;
    private final ClientService clientService;
    private final BidMapper bidMapper;

    @PostMapping
    public ResponseEntity<String> createBid(@Valid @RequestBody BidRequest bidRequest) throws JsonProcessingException {
        Auction auction = auctionService.findById(bidRequest.auctionId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found")
        );

        Client bidder = clientService.findByEmail(bidRequest.email()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bidder not found")
        );

        if(!bidService.isBidPriceValid(auction, bidRequest.bidPrice()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bid price");

        Bid newBid = bidService.save(auction, bidder, bidRequest.bidPrice());

        publishToChannel(auction.getChannelId(), bidMapper.toBidResponse(newBid));

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @GetMapping
    public ResponseEntity<List<BidResponse>> findAllAuctionBids(@NotNull @RequestParam Long auctionId) {
        Auction auction = auctionService.findById(auctionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found")
        );

        return ResponseEntity.ok(
                bidService.findAllByAuction(auction).stream()
                        .map(bidMapper::toBidResponse)
                        .toList()
        );
    }

    private void publishToChannel(String channelId, Object data) throws JsonProcessingException {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        try {
            ablyRest.channels.get(channelId).publish("create", json);
        } catch (AblyException err) {
            log.error(err.errorInfo.message);
        }
    }
}
