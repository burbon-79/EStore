package com.estore.controller;

import com.estore.dto.CartDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CartController {
    String addProductToCart(HttpSession session, @RequestParam int productId);
    String goToCartPage(HttpSession session, Model model);
    String checkout(@Valid CartDTO cartDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) throws MessagingException;
    String removeFromCart(HttpSession session, @RequestParam("idToDelete") int id);

}
