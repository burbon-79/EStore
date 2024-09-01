package com.estore.controller;

import com.estore.repository.SellerRepository;
import com.estore.repository.entity.SellerEntity;
import com.estore.service.PhotoService;
import jakarta.validation.Valid;
import com.estore.dto.ReviewDTO;
import com.estore.dto.SellerDTO;
import com.estore.repository.ProductRepository;
import com.estore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class SellerControllerImpl implements SellerController{
    private PasswordEncoder passwordEncoder;
    private SellerRepository sellerRepository;
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private PhotoService photoService;

    @Autowired
    public SellerControllerImpl(PasswordEncoder passwordEncoder, SellerRepository sellerRepository, ReviewRepository reviewRepository, ProductRepository productRepository, PhotoService photoService) {
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.photoService = photoService;
    }

    @GetMapping("/login")
    public String getToLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getToRegisterPage(Model model){
        model.addAttribute(new SellerDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewSeller(@Valid SellerDTO sellerDTO, Errors errors, Model model){
        if(errors.hasErrors()){
            return "register";
        }

        try {
            sellerRepository.getIdByEmail(sellerDTO.getEmail());
            model.addAttribute("duplicateError", "A user with this email address already exists");
            return "register";
        } catch (EmptyResultDataAccessException exception){
            sellerDTO.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
            sellerRepository.registerSeller(sellerDTO);
            return "redirect:/";
        }
    }

    @GetMapping("store")
    public String findIdOfAuthorizedStore(Principal principal){
        return "redirect:/store-" + sellerRepository.getIdByEmail(principal.getName());
    }

    @GetMapping("store-{id}")
    public String getToStorePage(@PathVariable int id, @RequestParam(required = false, defaultValue = "about") String page, Model model){
        SellerEntity seller;
        try {
            seller = sellerRepository.getStoreById(id);
        } catch (EmptyResultDataAccessException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("store", seller);

        if(Objects.equals(page, "about")) {
            model.addAttribute("reviews", reviewRepository.getReviewsByReferenceId(seller.getId(), "store"));
            if (!model.containsAttribute("reviewDTO")) {
                model.addAttribute(new ReviewDTO());
            }
            return "store_profile-about";
        } else if (Objects.equals(page, "products")){
            List<Map<String, Object>> listOfProducts = productRepository.getProductsOfThisSeller(id);
            Collections.shuffle(listOfProducts);
            model.addAttribute("products", listOfProducts);
            return "store_profile-products";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("changeStoreNameAndPhoto")
    public String changeNameAndPhoto(@RequestParam String page, @RequestParam MultipartFile photo, @RequestParam String name, Principal principal, RedirectAttributes redirectAttributes) throws IOException {
        int sellerId = sellerRepository.getIdByEmail(principal.getName());
        String url = "redirect:/store-" + sellerId + "?page=" + page;

        if(name.length()<2 || name.length()>99){
            redirectAttributes.addFlashAttribute("nameError", "The name must be from 2 to 99 characters.");
            return url;
        }

        if(photo!=null && photo.getSize()>0) {
            String linkToPhoto = photoService.savePhoto(sellerId, "store", photo);
            sellerRepository.setPhotoToStore(sellerId, linkToPhoto);
        }

        sellerRepository.setStoreName(sellerId, name);

        return url;
    }

    @PostMapping("changeStoreAbout")
    public String changeAbout(@RequestParam String text, Principal principal, RedirectAttributes redirectAttributes){
        int sellerId = sellerRepository.getIdByEmail(principal.getName());
        String url = "redirect:/store-" + sellerId + "?page=about";

        if(text.length()<2 || text.length()>999){
            redirectAttributes.addFlashAttribute("aboutError", "The about section must be from 2 to 999 characters.");
            return url;
        }

        sellerRepository.setStoreAbout(sellerId, text);
        return url;
    }
}
