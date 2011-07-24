package com.vmware.entertainmentetc.testing;

import com.vmware.entertainmentetc.services.bestbuy.BestBuyLocatorService;
import com.vmware.entertainmentetc.services.bestbuy.BestBuyStore;

public class TestBestBuyLocator {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BestBuyLocatorService s = new BestBuyLocatorService();
		
		Iterable<BestBuyStore> stores = s.findStoresByZip("94304");
		for (BestBuyStore store: stores) {
			System.out.println(store.toString());
		}
	}
}
