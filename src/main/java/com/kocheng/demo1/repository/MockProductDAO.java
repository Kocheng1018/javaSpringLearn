package com.kocheng.demo1.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.kocheng.demo1.entity.Product;
import com.kocheng.demo1.parameter.ProductQuery;

import jakarta.annotation.PostConstruct;

@Repository
public class MockProductDAO {
	private final static List<Product> productDB = new ArrayList<Product>();

	@PostConstruct
	private static void initDB() {
		productDB.add(new Product("B0001", "Android Development (Java)", 380));
		productDB.add(new Product("B0002", "Android Development (Kotlin)", 420));
		productDB.add(new Product("B0003", "Data Structure (Java)", 250));
		productDB.add(new Product("B0004", "Finance Management", 450));
		productDB.add(new Product("B0005", "Human Resource Management", 330));
	}

	public Optional<Product> find(String id) {
		return productDB.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	public List<Product> find(ProductQuery pq) {
		Comparator<Product> comparator = genSortComparator(pq.getOrder(), pq.getSortRule());
		return productDB.stream()
				.filter(p -> p.getName()
						.contains(Optional
								.ofNullable(pq.getKey())
								.orElse("")))
				.sorted(comparator)
				.collect(Collectors.toList());
	}

	public Product insert(Product p) {
		productDB.add(p);
		return p;
	}

	public Product update(String id, Product p) {
		Optional<Product> op = productDB.stream()
				.filter(i -> i.getId().equals(id))
				.findFirst();
		if (op.isPresent()) {
			Product item = op.get();
			item.setName(p.getName());
			item.setPrice(p.getPrice());
		}
		return p;
	}

	public void delete(String id) {
		productDB.removeIf(p -> p.getId().equals(id));
	}

	private Comparator<Product> genSortComparator(String orderBy, String sortRule) {
		Comparator<Product> comparator = (p1, p2) -> 0;
		if (Objects.isNull(orderBy) || Objects.isNull(comparator))
			return comparator;

		if (orderBy.equalsIgnoreCase("price")) {
			comparator = Comparator.comparing(Product::getPrice);
		} else if (orderBy.equalsIgnoreCase("name")) {
			comparator = Comparator.comparing(Product::getName);
		}

		return sortRule.equalsIgnoreCase("desc") ? comparator.reversed() : comparator;
	}

}
