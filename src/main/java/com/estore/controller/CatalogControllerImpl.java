package com.estore.controller;

import com.estore.repository.CategoryRepository;
import com.estore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogControllerImpl implements CatalogController{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public CatalogControllerImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<Map<String, Object>> listOfProducts = productRepository.getAllProducts();
        listOfProducts = selectProducts(listOfProducts);

        model.addAttribute("products", listOfProducts);
        return "index";
    }

    @GetMapping("category-{id}")
    public String categoryPage(@PathVariable("id") int categoryId, Model model){
        List<Map<String, Object>> listOfProducts = productRepository.getProductsOfThisCategory(categoryId);
        listOfProducts = selectProducts(listOfProducts);

        model.addAttribute("categoryName", categoryRepository.getCategoryNameById(categoryId));
        model.addAttribute("products", listOfProducts);
        return "category";
    }

    @GetMapping("search")
    public String search(@RequestParam String name, Model model){
        List<Map<String, Object>> listOfProducts = productRepository.searchByName(name);
        listOfProducts = selectProducts(listOfProducts);

        model.addAttribute("categoryName", "Search: \"" + name + '\"');
        model.addAttribute("products", listOfProducts);
        return "category";
    }

    private static List<Map<String, Object>> selectProducts(List<Map<String, Object>> listOfProducts) {
        Collections.shuffle(listOfProducts);
        int end = Math.min(listOfProducts.size(), 20);
        return listOfProducts.subList(0, end);
    }
}
