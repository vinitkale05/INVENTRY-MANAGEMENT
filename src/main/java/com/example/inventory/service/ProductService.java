package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public double getTotalInventoryValue() {
        return productRepository.findAll().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
    }

    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findAll().stream()
                .filter(p -> p.getQuantity() < threshold)
                .toList();
    }
}
