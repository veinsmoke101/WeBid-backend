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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


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

        if( auctionRequest.images().size() > 10 )
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuctionResponse> findById(@PathVariable("id") final Long id) {
        Auction auction = auctionService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found"));
        return ResponseEntity.ok(auctionMapper.auctionToAuctionResponse(auction));
    }

//    @GetMapping(value = "/{email}")
//    public ResponseEntity<List<AuctionResponse>> findByEmail(@PathVariable("email") final String email) {
//        Client author = clientService.findByEmail(email).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
//
//        List<AuctionResponse> auctionResponses = auctionService.findByAuthor(author).stream()
//                .map(auctionMapper::auctionToAuctionResponse)
//                .toList();
//
//        return ResponseEntity.ok(auctionResponses);
//    }


//    @GetMapping
//    public ResponseEntity<List<AuctionResponse>> findByTitle(
//            @RequestParam(required = false, name = "title") String title,
//            @RequestParam(required = false, name = "category") String category,
//            @RequestParam(required = false, name = "author") String author,
//            @RequestParam(required = false, name = "buyer") String buyer
//    ) {
//
//        if(author != null) {
//
//        }
//
//        List<AuctionResponse> auctionResponses = auctionService.findByTitle(title).stream()
//                .map(auctionMapper::auctionToAuctionResponse)
//                .toList();
//
//        return ResponseEntity.ok(auctionResponses);
//    }

    @GetMapping
    public ResponseEntity<List<AuctionResponse>> auctionResponses(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "45", name = "size") int size,
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "author") String authorEmail,
            @RequestParam(required = false, name = "buyer") String buyerEmail,
            @RequestParam(required = false, name = "category") String categoryName,
            @RequestParam(required = false, name = "start-date") LocalDateTime startDate,
            @RequestParam(required = false, name = "end-date") LocalDateTime endDate,
            @RequestParam(required = false, name = "starting-price") Double startingPrice,
            @RequestParam(required = false, name = "buyNowPrice") Double buyNowPrice,
            @RequestParam(required = false, name = "minPrice") Double minPrice,
            @RequestParam(required = false, name = "maxPrice") Double maxPrice,
            @RequestParam(required = false, name = "expireIn") String expireIn
    ) {
        // if the request contain author email and the author does not exist return empty list
            Client author = clientService.findByEmail(authorEmail).orElse(null);
            if(authorEmail != null && author == null)
                return ResponseEntity.ok().body(Collections.emptyList());

        // if the request contain buyer email and the buyer does not exist return empty list
        Client buyer = clientService.findByEmail(buyerEmail).orElse(null);
            if(buyerEmail != null && buyer == null)
                return ResponseEntity.ok().body(Collections.emptyList());

        // if the request contain category name and the category does not exist return empty list
        Category category = categoryService.findByName(categoryName).orElse(null);
            if(categoryName != null && category == null)
                return ResponseEntity.ok().body(Collections.emptyList());


        List<Auction> auctions = auctionService.search(
                page,
                size,
                title,
                author,
                buyer,
                category,
                startDate,
                endDate,
                startingPrice,
                buyNowPrice,
                minPrice,
                maxPrice
        );

        /* filter By expireIn request param */
        if(expireIn != null) {
            LocalDateTime expiryDate = LocalDateTime.now();
            boolean isExpireInValueValid = true;
            switch (expireIn){
                case "expired" -> expiryDate = LocalDateTime.now();
                case "day" -> expiryDate = LocalDateTime.now().plusDays(1);
                case "week" -> expiryDate = LocalDateTime.now().plusWeeks(1);
                case "month" -> expiryDate = LocalDateTime.now().plusMonths(1);
                default -> isExpireInValueValid = false;
            }

            if(isExpireInValueValid){
                LocalDateTime finalExpiryDate = expiryDate;
                auctions = auctions.stream()
                        .filter(auction -> auction.getStartDate().isBefore(finalExpiryDate))
                        .toList();
            }
        }
        /* filter By expireIn request param */


        List<AuctionResponse> auctionResponses = auctions.stream()
        .map(auctionMapper::auctionToAuctionResponse)
        .toList();
        return ResponseEntity.ok().body(auctionResponses);
    }

}
