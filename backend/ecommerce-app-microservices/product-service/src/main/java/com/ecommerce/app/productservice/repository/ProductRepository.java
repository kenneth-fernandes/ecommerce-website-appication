package com.ecommerce.app.productservice.repository;

import com.ecommerce.app.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
