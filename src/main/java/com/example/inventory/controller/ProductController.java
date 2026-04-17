package com.example.inventory.controller;

import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productService.getTotalProducts());
        model.addAttribute("totalValue", productService.getTotalInventoryValue());
        model.addAttribute("lowStock", productService.getLowStockProducts(10));
        model.addAttribute("products", productService.getAllProducts());
        return "dashboard";
    }

    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        productService.getProductById(id).ifPresent(p -> model.addAttribute("product", p));
        return "product-form";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
