package com.vmware.entertainmentetc.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Service for locating nearby Best Buy stores
 * @author Mike Stunes
 *
 */


public class BestBuyLocatorService {
	// Stuff this needs:
	// Datatype for Best Buy store
	// get by zip
	// get by city/state
	// get by lat/long
	// priv method for making API calls
	
	
	public BestBuyStore findStoreByZip(String zipCode) {
		
	}
	
	public BestBuyStore findStoreByCityState(String city, String state) {
		
	}
	
	public BestBuyStore findStoreByLatLng(String lat, String lng) {
		
	}
	
	
	// Parses an XML response from the Best Buy API into a bunch of
	// BestBuyStore objects
	private Iterable<BestBuyStore> parseApiResponse(InputStream resp) {
		try {
			List<BestBuyStore> output = new ArrayList<BestBuyStore>();
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resp);
			
			// doc is an XML document with root <stores> and a bunch of
			// <store> elements inside
			Element root = doc.getDocumentElement();
			NodeList stores = root.getElementsByTagName("store");
			
			for (int i = 0; i < stores.getLength(); i++) {
				Node n = stores.item(i);
				BestBuyStore s = new BestBuyStore();
				
				NodeList nc = n.getChildNodes();
				for (int c = 0; c < nc.getLength(); c++) {
					Node ncn = nc.item(c);
					if (ncn.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) ncn;
						
						if (e.getNodeName().equals("storeId")) {
							s.setStoreId(e.getTextContent());
						} else if (e.getNodeName().equals("name")) {
							s.setName(e.getTextContent());
						} else if (e.getNodeName().equals("address")) {
							s.setAddress(e.getTextContent());
						} else if (e.getNodeName().equals("city")) {
							s.setCity(e.getTextContent());
						} else if (e.getNodeName().equals("region")) {
							s.setRegion(e.getTextContent());
						} else if (e.getNodeName().equals("postalCode")) {
							s.setZipCode(e.getTextContent());
						} else if (e.getNodeName().equals("lat")) {
							s.setLat(e.getTextContent());
						} else if (e.getNodeName().equals("lng")) {
							s.setLng(e.getTextContent());
						} else if (e.getNodeName().equals("phone")) {
							s.setPhone(e.getTextContent());
						}
 					}
				}
				
				output.add(s);
			}
			
			return output;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<BestBuyStore>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<BestBuyStore>();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<BestBuyStore>();
		}
	}
	
	public static class BestBuyStore {
		private String storeId;
		private String name;
		private String address;
		private String city;
		private String region;
		private String zipCode;
		private String lat;
		private String lng;
		private String phone;
		
		
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getZipCode() {
			return zipCode;
		}
		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getLng() {
			return lng;
		}
		public void setLng(String lng) {
			this.lng = lng;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
}
