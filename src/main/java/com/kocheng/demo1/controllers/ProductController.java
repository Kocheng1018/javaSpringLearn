package com.kocheng.demo1.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kocheng.demo1.entity.Product;
import com.kocheng.demo1.exceptions.UnprocessableEntity;
import com.kocheng.demo1.parameter.ProductQuery;
import com.kocheng.demo1.services.ProductService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@Autowired
	private ProductService ps;

	// get by uri
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
		Product p = ps.getProduct(id);
		return ResponseEntity.ok().body(p);
	}

	// get mulit product by param
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(@ModelAttribute ProductQuery pq) {
		return ResponseEntity.ok().body(ps.getProducts(pq));
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product req) throws UnprocessableEntity {
		Product p = ps.createProduct(req);

		// 建立該商品得網址
		URI loc = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(p.getId())
				// 可多值 e.g.
				// .fromCurrentRequest().path("/{id}/{name}")
				// .buildAndExpand(p.getId(), p.getPrice())
				.toUri();

		// 201 createdcode 並將該商品網址放到 header Location 欄位
		return ResponseEntity.created(loc).body(p);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product req) {
		Product p = ps.replaceProduct(id, req);
		return ResponseEntity.ok().body(p);
	}

	@DeleteMapping("products/{id}")
	public ResponseEntity delProduct(@PathVariable String id) {
		ps.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}