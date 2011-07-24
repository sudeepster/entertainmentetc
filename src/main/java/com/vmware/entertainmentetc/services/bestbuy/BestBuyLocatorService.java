package com.vmware.entertainmentetc.services.bestbuy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
	
	// Radius to look in (in miles)
	private static final String distance = "5";
	private static final String reqBase = "http://api.remix.bestbuy.com/v1/stores";
	
	// TODO: fix this with global app properties
	private static final String apiKey = "?apiKey=";
	
	public Iterable<BestBuyStore> findStoresByZip(String zipCode) throws ApiRequestException, IOException {
		String thisReq = reqBase + "(area(\"" + zipCode + "\"," + distance + "))" + apiKey;
		return parseApiResponse(makeApiRequest(thisReq));
	}
	
	//public BestBuyStore findStoresByCityState(String city, String state) throws ApiRequestException, IOException {
	//	
	//}
	
	//public BestBuyStore findStoresByLatLng(String lat, String lng) throws ApiRequestException, IOException {
	//	
	//}
	
	
	private InputStream makeApiRequest(String reqString) throws IOException, ApiRequestException {
		HttpURLConnection req = (HttpURLConnection) new URL(reqString).openConnection();
		
		int responseCode = req.getResponseCode();
		if (responseCode != 200) {
			// Crap.
			throw new ApiRequestException();
		}
		
		return req.getInputStream();
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
}
