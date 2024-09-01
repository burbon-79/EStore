package com.estore.controller;

import com.estore.dto.ReviewDTO;
import jakarta.validation.Valid;
import com.estore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewControllerImpl implements ReviewController{
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("addProductReview")
    public String addProductReview(@Valid ReviewDTO reviewDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewDTO", bindingResult);
            redirectAttributes.addFlashAttribute(reviewDTO);
            return "redirect:/product-" + reviewDTO.getReferenceId();
        }

        reviewRepository.addReview(reviewDTO, "product");
        return "redirect:/product-" + reviewDTO.getReferenceId();
    }

    @PostMapping("addStoreReview")
    public String addStoreReview(@Valid ReviewDTO reviewDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewDTO", bindingResult);
            redirectAttributes.addFlashAttribute(reviewDTO);
            return "redirect:/store-" + reviewDTO.getReferenceId() + "?page=about";
        }

        reviewRepository.addReview(reviewDTO, "store");
        return "redirect:/store-" + reviewDTO.getReferenceId() + "?page=about";
    }
}
