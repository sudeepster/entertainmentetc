package com.vmware.entertainmentetc.services.bestbuy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mattwilliamsnyc.service.remix.Product;
import com.mattwilliamsnyc.service.remix.ProductsResponse;
import com.mattwilliamsnyc.service.remix.Remix;
import com.mattwilliamsnyc.service.remix.RemixException;
import com.mattwilliamsnyc.service.remix.StoresResponse;

public class BestBuyService {
	// TODO: bump this out into app's properties
	private static final String apiKey = "blarg";
	
	// TODO: let user specify this
	private static final String maxDist = "10";
	
	
	private final Remix remix;
	
	// TODO: check appropriate use of constructor in Spring service
	public BestBuyService() {
		remix = new Remix(apiKey);
	}
	
	public StoresResponse getProductInNearbyStores(String productSearch, String zipCode) throws RemixException {
		//Remix remix = new Remix(apiKey);
		
		// TODO: fix this so that it actually works
		//   - Request products by name, get SKUs
		//   - Request stores with those SKUs in stock
		
		//List<String> storeFilters = new ArrayList<String>();
		List<String> productFilters = new ArrayList<String>();
		
		//storeFilters.add("area(" + zipCode + "," + maxDist + ")");
		productFilters.add(urlify("search=" + productSearch));
		
		//return remix.getStoreAvailability(storeFilters, productFilters);
		List<String> SKUs = new ArrayList<String>();
		ProductsResponse matchingProducts = remix.getProducts(productFilters);
		for (Product product: matchingProducts.list()) {
			if (product.getSku() != null) SKUs.add(product.getSku());
		}
		
		// Next, search for nearby stores that have those SKUs
		StringBuilder skuString = new StringBuilder();
		Iterator<String> it = SKUs.iterator();
		skuString.append(it.next());
		for (; it.hasNext();) {
			skuString.append(",").append(it.next());
		}
		
		productFilters.clear();
		productFilters.add(urlify("sku in(" + skuString + ")"));
		List<String> storeFilters = new ArrayList<String>();
		storeFilters.add("area(" + zipCode + "," + maxDist + ")");
		
		return remix.getStoreAvailability(storeFilters, productFilters);
	}
	
	// Properly escape a string for putting into a URL. For now, focuses
	// on removing spaces
	private String urlify(String url) {
		return url.replace(" ", "%20");
	}
}