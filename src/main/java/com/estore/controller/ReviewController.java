package com.estore.controller;

import com.estore.dto.ReviewDTO;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ReviewController {
    String addProductReview(@Valid ReviewDTO reviewDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);
    String addStoreReview(@Valid ReviewDTO reviewDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);
}
