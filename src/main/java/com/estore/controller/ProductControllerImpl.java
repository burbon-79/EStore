package com.estore.controller;

import com.estore.dto.ReviewDTO;
import com.estore.repository.SellerRepository;
import com.estore.repository.entity.ProductEntity;
import com.estore.service.PhotoService;
import jakarta.validation.Valid;
import com.estore.dto.ProductDTO;
import com.estore.repository.ProductRepository;
import com.estore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
public class ProductControllerImpl implements ProductController{
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private ReviewRepository reviewRepository;
    private PhotoService photoService;

    @Autowired
    public ProductControllerImpl(ProductRepository productRepository, SellerRepository sellerRepository, ReviewRepository reviewRepository, PhotoService photoService) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.reviewRepository = reviewRepository;
        this.photoService = photoService;
    }

    @GetMapping("addProduct")
    public String getToAddingProductPage(Model model, Principal principal){
        model.addAttribute(new ProductDTO());
        model.addAttribute("store_id", sellerRepository.getIdByEmail(principal.getName()));
        model.addAttribute("store_name", sellerRepository.getStoreNameByEmail(principal.getName()));
        return "add_product";
    }

    @PostMapping("addProduct")
    public String createNewProduct(@Valid ProductDTO productDTO, Errors errors, @RequestParam("photo") MultipartFile photo, Principal principal) throws IOException {
        if(errors.hasErrors()){
            return "add_product";
        }

        if(Objects.equals(productDTO.getDescription(), "")){
            productDTO.setDescription("No description");
        }

        int sellerId = sellerRepository.getIdByEmail(principal.getName());
        int newProductId = productRepository.addNewProduct(productDTO, sellerId);
        if(photo!=null && photo.getSize()>0) {
            String linkToPhoto = photoService.savePhoto(newProductId, "product", photo);
            productRepository.setPhotoToProduct(newProductId, linkToPhoto);
        }

        return "redirect:/store-"+ sellerId +"?page=products";
    }

    @GetMapping("product-{id}")
    public String getProduct(@PathVariable int id, Model model){
        ProductEntity product;
        try {
            product = productRepository.getProductById(id);
        } catch (EmptyResultDataAccessException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("product", product);
        model.addAttribute("store_name", sellerRepository.getStoreNameById(product.getSellerId()));
        model.addAttribute("reviews", reviewRepository.getReviewsByReferenceId(product.getId(), "product"));

        if(!model.containsAttribute("reviewDTO")) {
            model.addAttribute(new ReviewDTO());
        }

        return "product";
    }
}
