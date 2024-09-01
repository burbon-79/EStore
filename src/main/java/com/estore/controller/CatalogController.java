package com.estore.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CatalogController {
    String indexPage(Model model);
    String categoryPage(@PathVariable("id") int categoryId, Model model);
    String search(@RequestParam String name, Model model);
}
