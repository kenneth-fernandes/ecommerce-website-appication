package com.ecommerce.web.app.productservice.repository;

import com.ecommerce.web.app.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
