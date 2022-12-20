package com.kocheng.demo1.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kocheng.demo1.entity.Product;

@Repository
public interface ProductRepostory extends MongoRepository<Product, String> {

    List<Product> findByPriceBetweenAndNameLikeIgnoreCase(int priceForm, int priceTo, String keyword, Sort sort);
}
