package com.kocheng.demo1.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.kocheng.demo1.entity.Product;
import com.kocheng.demo1.exceptions.UnprocessableEntity;
import com.kocheng.demo1.parameter.ProductQuery;
import com.kocheng.demo1.repository.MockProductDAO;
import com.kocheng.demo1.repository.ProductRepostory;

@Service
public class ProductService {
    @Autowired
    private MockProductDAO productDAO;

    @Autowired
    private ProductRepostory repostory;

    public Product createProduct(Product p) throws UnprocessableEntity {
        Product item = new Product(p.getId(), p.getName(), p.getPrice());

        return repostory.insert(item);

        // boolean isIdDuplicated = productDAO.find(p.getId()).isPresent();
        // if (isIdDuplicated)
        // throw new UnprocessableEntity("The id of the Product is duplicated");

        // Product item = new Product(p.getId(), p.getName(), p.getPrice());
        // return productDAO.insert(item);
    }

    public Product getProduct(String id) throws NotFoundException {
        return repostory
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Can's find product."));

        // Optional<Product> op = productDAO.find(id);
        // if(!op.isPresent()) throw new NotFoundException("Can's find product.");
        // return op.get();
        // return productDAO.find(id).orElseThrow(() -> new NotFound("Can's find
        // product."));
    }

    public List<Product> getProducts(ProductQuery pq) {
        String keyword = Optional.ofNullable(pq.getKey()).orElse("");
        int priceForm = Optional.ofNullable(pq.getPriceFrom()).orElse(0);
        int priceTo = Optional.ofNullable(pq.getPriceTo()).orElse(Integer.MAX_VALUE);

        Sort sort = genSortingStrategy(pq.getOrder(), pq.getSortRule());

        return repostory.findByPriceBetweenAndNameLikeIgnoreCase(priceForm, priceTo, keyword, sort);
    }

    public Product replaceProduct(String id, Product p) {
        Product op = getProduct(id);

        op.setName(p.getName());
        op.setPrice(p.getPrice());

        return repostory.save(op);
        // Product item = getProduct(id);
        // return productDAO.update(item.getId(), p);
    }

    public void deleteProduct(String id) {
        Product p = getProduct(id);

        repostory.deleteById(p.getId());

        // Product item = getProduct(id);
        // productDAO.delete(item.getId());
    }

    private Sort genSortingStrategy(String orderBy, String sortRule) {
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction dir = Sort.Direction.fromString(sortRule);
            sort = Sort.by(dir, orderBy);
        }
        return sort;
    }

}
