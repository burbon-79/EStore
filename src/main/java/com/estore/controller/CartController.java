package com.estore.controller;

import com.estore.dto.CartDTO;
import com.estore.repository.SellerRepository;
import com.estore.repository.entity.ProductEntity;
import com.estore.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.estore.repository.ProductRepository;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private EmailService emailService;

    @Autowired
    public CartController(ProductRepository productRepository, SellerRepository sellerRepository, EmailService emailService) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.emailService = emailService;
    }

    @PostMapping("addToCart")
    public String addProductToCart(HttpSession session, @RequestParam int productId){
        ArrayList<ProductEntity> cart = (ArrayList<ProductEntity>) session.getAttribute("cart");
        if(cart == null){
            cart = new ArrayList<>();
        }
        cart.add(productRepository.getProductById(productId));
        session.setAttribute("cart", cart);
        return "redirect:/product-" + productId;
    }

    @GetMapping("cart")
    public String goToCartPage(HttpSession session, Model model){
        if(!model.containsAttribute("cartDTO")) {
            model.addAttribute(new CartDTO());
        }

        if (isCartEmpty(session)){
            model.addAttribute("cartIsEmpty", true);
            return "order";
        }

        model.addAttribute("cartIsEmpty", false);
        ArrayList<ProductEntity> cart = (ArrayList<ProductEntity>) session.getAttribute("cart");
        ArrayList<String> storeNames = new ArrayList<>();
        double totalPrice = 0;
        for (ProductEntity product : cart) {
            storeNames.add(sellerRepository.getStoreNameById(product.getSellerId()));
            totalPrice+=product.getPrice();
        }
        model.addAttribute("storeNames", storeNames);
        model.addAttribute("totalPrice", String.format("%.2f", totalPrice));

        return "order";
    }

    @PostMapping("checkout")
    public String checkout(@Valid CartDTO cartDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) throws MessagingException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cartDTO", bindingResult);
            redirectAttributes.addFlashAttribute(cartDTO);
            if (isCartEmpty(session)){
                redirectAttributes.addFlashAttribute("emptyCartError", "Cart is empty");
            }
            return "redirect:/cart";
        }

        ArrayList<ProductEntity> cart = (ArrayList<ProductEntity>) session.getAttribute("cart");
        for (ProductEntity product : cart) {
            try {
                int amount = Integer.parseInt(request.getParameter("amount" + product.getId()));
                if(amount<1){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cartDTO", bindingResult);
                redirectAttributes.addFlashAttribute(cartDTO);
                redirectAttributes.addFlashAttribute("AmountError", "One of the Amount fields is entered incorrectly");
                return "redirect:/cart";
            }
        }

        Map<Integer, ArrayList<ProductEntity>> orderedProducts = new HashMap<>();
        for (ProductEntity thisProduct : cart) {
            int sellerId = thisProduct.getSellerId();

            ArrayList<ProductEntity> productsOfThisSeller;
            if (orderedProducts.containsKey(sellerId)) {
                productsOfThisSeller = orderedProducts.get(sellerId);
            } else {
                productsOfThisSeller = new ArrayList<>();
            }

            productsOfThisSeller.add(thisProduct);
            orderedProducts.put(sellerId, productsOfThisSeller);
        }

        Integer[] mapKeySet = orderedProducts.keySet().toArray(new Integer[0]);
        for (Integer sellerId : mapKeySet) {
            sendEmailToSeller(cartDTO, request, sellerId, orderedProducts);
        }

        sendEmailToClient(cartDTO, request, mapKeySet, orderedProducts);

        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("removeFromCart")
    public String removeFromCart(HttpSession session, @RequestParam("idToDelete") int id){
        ArrayList<ProductEntity> list = (ArrayList<ProductEntity>) session.getAttribute("cart");
        list.remove(id);
        if(list.isEmpty()){
            session.removeAttribute("cart");
        } else {
            session.setAttribute("cart", list);
        }

        return "redirect:/cart";
    }

    private static boolean isCartEmpty(HttpSession session) {
        ArrayList<ProductEntity> cart = (ArrayList<ProductEntity>) session.getAttribute("cart");
        return cart == null;
    }

    private void sendEmailToSeller(CartDTO cartDTO, HttpServletRequest request, Integer sellerId, Map<Integer, ArrayList<ProductEntity>> orderedProducts) throws MessagingException {
        double total = 0;
        ArrayList<ProductEntity> orderedProductsOfThisSeller = orderedProducts.get(sellerId);

        ArrayList<String[]> productsToLetter = new ArrayList<>();
        for (ProductEntity thisProduct : orderedProductsOfThisSeller) {
            int amount = Integer.parseInt(request.getParameter("amount" + thisProduct.getId()));
            productsToLetter.add(new String[]{thisProduct.getName(), String.valueOf(thisProduct.getPrice()), String.valueOf(amount)});

            total += thisProduct.getPrice() * amount;
        }

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        Template template = velocityEngine.getTemplate("templates/email/mailTemplate.vm");

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("headerText", "You've received a new order:");
        velocityContext.put("clientInfo", cartDTO);
        velocityContext.put("orderedProductsOfThisSeller", productsToLetter);
        velocityContext.put("total", total);

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        emailService.sendEmail(sellerRepository.getEmailById(sellerId), "New order", stringWriter.toString());
    }

    private void sendEmailToClient(CartDTO cartDTO, HttpServletRequest request, Integer[] mapKeySet, Map<Integer, ArrayList<ProductEntity>> orderedProducts) throws MessagingException {
        double total = 0;
        ArrayList<String[]> productsToLetter = new ArrayList<>();

        for (Integer sellerId : mapKeySet) {
            ArrayList<ProductEntity> productsOfThisSeller = orderedProducts.get(sellerId);
            for (ProductEntity thisProduct : productsOfThisSeller) {
                int amount = Integer.parseInt(request.getParameter("amount" + thisProduct.getId()));
                productsToLetter.add(new String[]{thisProduct.getName(), String.valueOf(thisProduct.getPrice()), String.valueOf(amount)});

                total += thisProduct.getPrice() * amount;
            }
        }

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        Template template = velocityEngine.getTemplate("templates/email/mailTemplate.vm");

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("headerText", "Your receipt:");
        velocityContext.put("clientInfo", cartDTO);
        velocityContext.put("orderedProductsOfThisSeller", productsToLetter);
        velocityContext.put("total", total);

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        emailService.sendEmail(cartDTO.getEmail(), "Your receipt", stringWriter.toString());
    }
}
