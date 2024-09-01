package com.estore.controller;

import com.estore.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface ProductController {
    String getToAddingProductPage(Model model, Principal principal);
    String createNewProduct(@Valid ProductDTO productDTO, Errors errors, @RequestParam("photo") MultipartFile photo, Principal principal) throws IOException;
    String getProduct(@PathVariable int id, Model model);
}
