package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        simulateLatency();
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        log.info("Fetching product with id={}", id);
        simulateLatency();
        return repository.findById(id);
    }

    private void simulateLatency() {
        try {
            Thread.sleep((long) (Math.random() * 50 + 10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
