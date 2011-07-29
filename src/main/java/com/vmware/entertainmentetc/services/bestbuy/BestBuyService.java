package com.vmware.entertainmentetc.services.bestbuy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mattwilliamsnyc.service.remix.Product;
import com.mattwilliamsnyc.service.remix.ProductsResponse;
import com.mattwilliamsnyc.service.remix.Remix;
import com.mattwilliamsnyc.service.remix.RemixException;
import com.mattwilliamsnyc.service.remix.Store;
import com.mattwilliamsnyc.service.remix.StoresResponse;
import com.vmware.entertainmentetc.model.ProductStores;

public class BestBuyService {
	// TODO: let user specify this
	private static final String maxDist = "10";

	private final Remix remix;
	
	public BestBuyService(String apiKey) {
		remix = new Remix(apiKey);
	}
	
	public List<ProductStores> getProductStores(String productSearch, String location, String category) throws RemixException {
		StoresResponse r = getProductInNearbyStores(productSearch, location, category);
		
		if (r == null) {
			return new ArrayList<ProductStores>();
		}
		
		List<ProductStores> l = new ArrayList<ProductStores>();
		
		for (Store s: r.list()) {
			for (Product p: s.getProducts()) {
				l.add(new ProductStores(p, s));
			}
		}
		
		return l;
	}
	
	public StoresResponse getProductInNearbyStores(String productSearch, String zipCode, String category) throws RemixException {
		if (zipCode == null) {
			return null;
		}

		// First, get SKUs for matching products
		List<String> productFilters = new ArrayList<String>();
		
		productFilters.add(urlify("search=" + decommaify(productSearch)));
		productFilters.add("categoryPath.id=" + category);
		
		List<String> SKUs = new ArrayList<String>();
		ProductsResponse matchingProducts = remix.getProducts(productFilters);
		for (Product product: matchingProducts.list()) {
			if (product.getSku() != null) SKUs.add(product.getSku());
		}
		
		// Next, search for nearby stores that have those SKUs
		if (SKUs.isEmpty()) {
			// TODO: this is probably a crappy way to fix this
			return null;
		}
		StringBuilder skuString = new StringBuilder();
		Iterator<String> it = SKUs.iterator();
		skuString.append(it.next());
		for (; it.hasNext();) {
			skuString.append(",").append(it.next());
		}
		
		productFilters.clear();
		productFilters.add(urlify("sku in(" + skuString + ")"));
		// Here, skuString needs to keep its commas for the API to work right
		System.out.println("SKU string: " + urlify("sku in(" + skuString + ")"));
		List<String> storeFilters = new ArrayList<String>();
		storeFilters.add("area(" + zipCode + "," + maxDist + ")");
		
		return remix.getStoreAvailability(storeFilters, productFilters);
	}
	
	// Properly escape a string for putting into a URL. For now, focuses
	// on removing spaces
	private String urlify(String url) {
		return url.replace(" ", "%20");
	}
	
	// Remove commas from a search string
	private String decommaify(String url) {
		return url.replace(",", " ");
	}
}
