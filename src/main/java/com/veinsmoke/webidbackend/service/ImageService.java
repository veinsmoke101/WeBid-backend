package com.veinsmoke.webidbackend.service;

import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Image;
import com.veinsmoke.webidbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    @Transactional
    public List<Image> saveAll(List<MultipartFile> images, Auction auction) {
        return images.stream()
                .map(cloudinaryService::uploadImage)
                .map(url -> new Image(url, auction))
                .map(imageRepository::save)
                .toList();
    }

    public Image find(Long id) {
        return imageRepository.findById(id).orElseThrow();
    }
}
