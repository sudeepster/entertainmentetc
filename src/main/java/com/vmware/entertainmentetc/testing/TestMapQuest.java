package com.vmware.entertainmentetc.testing;

import com.vmware.entertainmentetc.services.mapquest.MapQuestService;

public class TestMapQuest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapQuestService svc = new MapQuestService();
		
		System.out.println(svc.getLatLngFromCityState("Madison, South dakota"));
	}

}
