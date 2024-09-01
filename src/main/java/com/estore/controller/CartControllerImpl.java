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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartControllerImpl implements CartController{
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private EmailService emailService;

    @Autowired
    public CartControllerImpl(ProductRepository productRepository, SellerRepository sellerRepository, EmailService emailService) {
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
        StringBuilder stringBuilder = new StringBuilder();
        double total = 0;

        stringBuilder.append("<html><body><h1>You've received a new order:</h1><div><h2>Client information:</h2>");
        stringBuilder.append("<span>Full name: ").append(cartDTO.getFullName()).append("</span><br>");
        stringBuilder.append("<span>Phone number: ").append(cartDTO.getPhoneNumber()).append("</span><br>");
        stringBuilder.append("<span>Email: ").append(cartDTO.getEmail()).append("</span><br>");
        stringBuilder.append("<span>Shipping: ").append(cartDTO.getShipping()).append("</span><br>");
        stringBuilder.append("<span>City: ").append(cartDTO.getCity()).append("</span><br>");
        stringBuilder.append("<span>Payment: ").append(cartDTO.getPayment()).append("</span><br>");
        stringBuilder.append("</div><div><h2>Order:</h2><table><tr><td>Name</td><td>Price</td><td>Amount</td></tr>");

        ArrayList<ProductEntity> productsOfThisSeller = orderedProducts.get(sellerId);
        for (ProductEntity thisProduct : productsOfThisSeller) {
            int amount = Integer.parseInt(request.getParameter("amount" + thisProduct.getId()));

            stringBuilder.append("<tr><td>").append(thisProduct.getName()).append("</td>");
            stringBuilder.append("<td>").append(thisProduct.getPrice()).append("$</td>");
            stringBuilder.append("<td>").append(amount).append("</td></tr>");

            total += thisProduct.getPrice() * amount;
        }

        stringBuilder.append("</table><h3>Total: ").append(total).append("$</h3></div></body></html>");

        emailService.sendEmail(sellerRepository.getEmailById(sellerId), "New order", stringBuilder.toString());
    }

    private void sendEmailToClient(CartDTO cartDTO, HttpServletRequest request, Integer[] mapKeySet, Map<Integer, ArrayList<ProductEntity>> orderedProducts) throws MessagingException {
        StringBuilder stringBuilder = new StringBuilder();
        double total = 0;

        stringBuilder.append("<html><body><h1>Your receipt:</h1><div><h2>Client information:</h2>");
        stringBuilder.append("<span>Full name: ").append(cartDTO.getFullName()).append("</span><br>");
        stringBuilder.append("<span>Phone number: ").append(cartDTO.getPhoneNumber()).append("</span><br>");
        stringBuilder.append("<span>Email: ").append(cartDTO.getEmail()).append("</span><br>");
        stringBuilder.append("<span>Shipping: ").append(cartDTO.getShipping()).append("</span><br>");
        stringBuilder.append("<span>City: ").append(cartDTO.getCity()).append("</span><br>");
        stringBuilder.append("<span>Payment: ").append(cartDTO.getPayment()).append("</span><br>");
        stringBuilder.append("</div><div><h2>Order:</h2><table><tr><td>Name</td><td>Price</td><td>Amount</td></tr>");

        for (Integer sellerId : mapKeySet) {
            ArrayList<ProductEntity> productsOfThisSeller = orderedProducts.get(sellerId);
            for (ProductEntity thisProduct : productsOfThisSeller) {
                int amount = Integer.parseInt(request.getParameter("amount" + thisProduct.getId()));

                stringBuilder.append("<tr><td>").append(thisProduct.getName()).append("</td>");
                stringBuilder.append("<td>").append(thisProduct.getPrice()).append("$</td>");
                stringBuilder.append("<td>").append(amount).append("</td></tr>");

                total += thisProduct.getPrice() * amount;
            }
        }

        stringBuilder.append("</table><h3>Total: ").append(total).append("$</h3></div></body></html>");

        emailService.sendEmail(cartDTO.getEmail(), "Your receipt", stringBuilder.toString());
    }
}
