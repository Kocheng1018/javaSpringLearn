package com.kocheng.demo1.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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

import com.kocheng.demo1.Model.Product;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	private final static List<Product> productDB = new ArrayList<Product>();

	@PostConstruct
	private static void initDB() {
		productDB.add(new Product("B0001", "Android Development (Java)", 380));
		productDB.add(new Product("B0002", "Android Development (Kotlin)", 420));
		productDB.add(new Product("B0003", "Data Structure (Java)", 250));
		productDB.add(new Product("B0004", "Finance Management", 450));
		productDB.add(new Product("B0005", "Human Resource Management", 330));
	}

	// get by uri
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
		Optional<Product> item = productDB.stream().filter(p -> p.getId().equals(id)).findFirst();

		if (!item.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Product p = item.get();
		return ResponseEntity.ok().body(p);
	}

	// get mulit product by param
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(@ModelAttribute Product.ProductQuery pq) {
		List<Product> products = productDB.stream()
				.filter(p -> p.getName().toUpperCase().contains(pq.getKey()))
				.sorted((p1, p2) -> {
					if (Objects.isNull(pq.getOrder()) ||
							Objects.isNull(pq.getSortRule()) ||
							pq.getOrder().isEmpty())
						return 0;

					if (pq.getOrder().toLowerCase().equals("price")) {
						if(pq.getSortRule().equals("desc")) return p1.getPrice() < p2.getPrice() ? 1 : -1;
						return p1.getPrice() > p2.getPrice() ? 1 : -1;
					}

					if (pq.getOrder().toLowerCase().equals("name")) {
						if(pq.getSortRule().equals("desc")) return p1.getName().length() < p2.getName().length() ? 1 : -1;
						return p1.getName().length() > p2.getName().length() ? 1 : -1;
					}
					return 0;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(products);
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product req) {
		boolean isIdDuplicated = productDB.stream().anyMatch(p -> p.getId().equals(req.getId()));
		if (isIdDuplicated) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		Product p = new Product(
				req.getId(),
				req.getName(),
				req.getPrice());

		productDB.add(p);

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
		Optional<Product> op = productDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if (!op.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Product p = op.get();
		p.setName(req.getName());
		p.setPrice(req.getPrice());
		return ResponseEntity.ok().body(p);
	}

	@DeleteMapping("products/{id}")
	public ResponseEntity<Void> delProduct(@PathVariable String id) {
		boolean isRemoved = productDB.removeIf(p -> p.getId().equals(id));
		return isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}