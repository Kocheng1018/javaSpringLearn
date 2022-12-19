package com.kocheng.demo1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.kocheng.demo1.entity.Product;
import com.kocheng.demo1.exceptions.UnprocessableEntity;
import com.kocheng.demo1.parameter.ProductQuery;
import com.kocheng.demo1.repository.MockProductDAO;



@Service
public class ProductService {
    @Autowired
    private MockProductDAO productDAO;

    public Product createProduct(Product p) throws UnprocessableEntity {
        boolean isIdDuplicated = productDAO.find(p.getId()).isPresent();
        if(isIdDuplicated) throw new UnprocessableEntity("The id of the Product is duplicated");

        Product item = new Product(p.getId(), p.getName(), p.getPrice());
        return productDAO.insert(item);
    }

    public Product getProduct(String id) throws NotFoundException {
        Optional<Product> op = productDAO.find(id);
        if(!op.isPresent()) throw new NotFoundException("Can's find product.");
        return op.get();
        // return productDAO.find(id).orElseThrow(() -> new NotFound("Can's find product."));
    }

    public List<Product> getProducts(ProductQuery pq) {
        return productDAO.find(pq);
    }

    public Product replaceProduct(String id, Product p) {
        Product item = getProduct(id);
        return productDAO.update(item.getId(), p);
    }

    public void deleteProduct(String id) {
        Product item = getProduct(id);
        productDAO.delete(item.getId());
    }

}
