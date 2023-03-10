package com.kocheng.demo1.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kocheng.demo1.entity.Product;

public interface ProductRepostory extends MongoRepository<Product, String> {

    @Query("{'$and': [{'price': {'$gte': ?0, '$lte': ?1}}, {'name': {'$regex': ?2, '$options': 'i'}}]}")
    List<Product> findByPriceBetweenAndNameLikeIgnoreCase(int priceFrom, int priceTo, String name, Sort sort);

    @Query("{'price': {'$gte': ?0, '$lte': ?1}}")
    List<Product> findByPriceBetween(int from, int to);

    @Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    List<Product> findByNameLikeIgnoreCase(String name);

    @Query("{'$and': [{'price': {'$gte': ?0, '$lte': ?1}}, {'name': {'$regex': ?2, '$options': 'i'}}]}")
    List<Product> findByPriceBetweenAndNameLikeIgnoreCase(int priceFrom, int priceTo, String name);

    @Query(value = "{'_id': {'$in': ?0}}", count = true)
    int countByIdIn(List<String> ids);

    @Query(value = "{'_id': {'$in': ?0}}", exists = true)
    boolean existsByIdIn(List<String> ids);

    @Query(delete = true)
    void deleteByIdIn(List<String> ids);

    @Query(sort = "{'name': 1, 'price': -1}")
    List<Product> findByIdInOrderByNameAscPriceDesc(List<String> ids);
}
