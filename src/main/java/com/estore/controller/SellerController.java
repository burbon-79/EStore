package com.estore.controller;

import com.estore.dto.SellerDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

public interface SellerController {
    String getToLoginPage();
    String getToRegisterPage(Model model);
    String registerNewSeller(@Valid SellerDTO sellerDTO, Errors errors, Model model);
    String findIdOfAuthorizedStore(Principal principal);
    String getToStorePage(@PathVariable int id, @RequestParam(required = false, defaultValue = "about") String page, Model model);
    String changeNameAndPhoto(@RequestParam String page, @RequestParam MultipartFile photo, @RequestParam String name, Principal principal, RedirectAttributes redirectAttributes) throws IOException;
    String changeAbout(@RequestParam String text, Principal principal, RedirectAttributes redirectAttributes);
}
