package com.veinsmoke.webidbackend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    public String uploadImage(MultipartFile image)  {
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("folder", "webid/auction/"));
            log.info("Image uploaded to cloudinary, url: {}", uploadResult.get("url"));
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
        }
    }
}