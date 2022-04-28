package com.ecommerce.model;

import java.util.List;

public class CartApiResponse {

    private List<Product> carts;

	public List<Product> getCarts() {
		return carts;
	}

	public void setCarts(List<Product> carts) {
		this.carts = carts;
	}

	@Override
	public String toString() {
		return "CartApiResponse [carts=" + carts + "]";
	}

	public CartApiResponse(List<Product> carts) {
		super();
		this.carts = carts;
	}

   
}
