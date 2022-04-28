package com.ecommerce.model;

import java.util.List;

public class ProductApiResponse {

    private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ProductApiResponse [products=" + products + "]";
	}

	public ProductApiResponse(List<Product> products) {
		super();
		this.products = products;
	}

}
