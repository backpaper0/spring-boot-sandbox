package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final List<Product> products = List.of(
            new Product(1L, "ワイヤレスヘッドフォン", "高音質ノイズキャンセリング対応", 15800),
            new Product(2L, "メカニカルキーボード", "Cherry MX 青軸搭載", 12500),
            new Product(3L, "4Kモニター", "27インチ IPS パネル", 45000),
            new Product(4L, "USBマイク", "コンデンサー型 カーディオイド", 8900),
            new Product(5L, "ウェブカメラ", "1080p オートフォーカス対応", 6500)
    );

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }
}
