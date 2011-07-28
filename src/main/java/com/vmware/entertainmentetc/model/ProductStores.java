package com.vmware.entertainmentetc.model;

import com.mattwilliamsnyc.service.remix.Product;
import com.mattwilliamsnyc.service.remix.Store;

public class ProductStores {
	private Product product;
	private Store store;
	
	public ProductStores(Product p, Store s) {
		product = p;
		store = s;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
}
