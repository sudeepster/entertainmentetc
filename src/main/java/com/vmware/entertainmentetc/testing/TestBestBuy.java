package com.vmware.entertainmentetc.testing;

import com.mattwilliamsnyc.service.remix.ErrorDocument;
import com.mattwilliamsnyc.service.remix.Product;
import com.mattwilliamsnyc.service.remix.Store;
import com.mattwilliamsnyc.service.remix.StoresResponse;
import com.vmware.entertainmentetc.services.bestbuy.BestBuyService;

public class TestBestBuy {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BestBuyService svc = new BestBuyService();
		StoresResponse response = svc.getProductInNearbyStores("good news for people who love bad news", "94304");
		
		if (!response.isError()) {
			System.out.println("Search results follow:");
			for (Store store: response.list()) {
				System.out.println(store.getName() + ";" + store.getDistance());
				for (Product product: store.getProducts()) {
					if (product.hasInStoreAvailability()) {
						System.out.println(product.getName() + ";" + product.getSku() + ";" + product.getSalePrice());
					}
				}
				System.out.println();
			}
		} else {
			ErrorDocument error = response.getError();
			if (error != null) {
				System.out.println(error.getStatus());
				System.out.println(error.getMessage());
			} else {
				System.out.println("Got null error message");
			}
		}
//		// TODO Auto-generated method stub
//		Remix remix = new Remix("5tyye5xz9zfw7csuu7ssnduw");
//		
//		List<String> storeFilters = new ArrayList<String>();
//		List<String> productFilters = new ArrayList<String>();
//		
//		storeFilters.add("area(94304,20)");
//		//productFilters.add("sku=9456803");
//		productFilters.add("search=good%20night%20and%20good%20luck");
//		//productFilters.add("")
//		//productFilters.add("manufacturer=nikon");
		
		
		
//		try {
//			ProductsResponse response = remix.getProducts(productFilters);
//			
//			System.out.println("Results follow:");
//			
//			for (Product product: response.list()) {
//				System.out.println(product.getName() + ";" + product.getSku());
//			}
////			StoresResponse response = remix.getStoreAvailability(storeFilters, productFilters);
////			
////			if (!response.isError()) {
////				for (Store store: response.list()) {
////					System.out.println(store.getName() + " (" + store.getDistance() + " miles)");
////					for (Product product: store.getProducts()) {
////						if (product.hasInStoreAvailability()) {
////							System.out.println(product.getName());
////							System.out.println("Available for $" + product.getSalePrice());
////						}
////					}
////					System.out.println();
////				}
////			} else {
////				ErrorDocument error = response.getError();
////				if (error != null) {
////					System.out.println(error.getStatus());
////					System.out.println(error.getMessage());
////					System.out.println("Examples:");
////					for (String example: error.getExamples()) {
////						System.out.println(example);
////					}
////				}
////			}
//		} catch (RemixException e) {
//			e.printStackTrace();
//		}
	}

}
