package com.estore.controller;

import com.estore.repository.ProductRepository;
import com.estore.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.Objects;

@Controller
public class ImageController {
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;

    @Autowired
    public ImageController(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("{type}/image-{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String type, @PathVariable int id) throws IOException {
        byte[] photo = new byte[0];
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (Objects.equals(type, "product")){
            photo = productRepository.getProductPhotoById(id);
        } else if (Objects.equals(type, "store")){
            photo = sellerRepository.getStorePhotoById(id);
        }

        if(photo==null){
            photo = new ClassPathResource("static/images/no-image.jpg").getContentAsByteArray();
        }

        return new ResponseEntity<>(photo, headers, HttpStatus.OK);
    }
}
