package com.vmware.entertainmentetc.services.mapquest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MapQuestService {
	private final String apiKey;
	
	public MapQuestService(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getLatLngFromCityState(String cityState) {
		try {
			String url = "http://www.mapquestapi.com/geocoding/v1/address?key=" + apiKey + "&location=" + cityState + "&outFormat=xml&maxResults=1";
			HttpURLConnection req = (HttpURLConnection) new URL(urlify(url)).openConnection();
			
			int responseCode = req.getResponseCode();
			if (responseCode != 200) {
				// Well darn...
				//System.out.println("Response code != 200");
				return null;
			}
			
			InputStream response = req.getInputStream();
			
			// Parse out the returned XML
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response);
			// want these XPaths:
			// /response/results/result/locations/location/displayLatLng/latLng/lat
			// /response/results/result/locations/location/displayLatLng/latLng/lng
			XPathExpression latPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/locations/location/displayLatLng/latLng/lat");
			XPathExpression lngPath = XPathFactory.newInstance().newXPath().compile("/response/results/result/locations/location/displayLatLng/latLng/lng");
			
			String lat;
			String lng;
			
			NodeList latNodes = (NodeList) latPath.evaluate(doc, XPathConstants.NODESET);
			if (latNodes.getLength() != 0) {
				lat = latNodes.item(0).getTextContent();
			} else {
				return null;
			}
			
			NodeList lngNodes = (NodeList) lngPath.evaluate(doc, XPathConstants.NODESET);
			if (lngNodes.getLength() != 0) {
				lng = lngNodes.item(0).getTextContent();
			} else {
				return null;
			}
			
			return lat + "," + lng;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	private String urlify(String url) {
		return url.replace(" ", "%20");
	}
}
