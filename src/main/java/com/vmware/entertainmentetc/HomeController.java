/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vmware.entertainmentetc;

import java.util.List;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.LikeOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mattwilliamsnyc.service.remix.RemixException;
import com.vmware.entertainmentetc.model.ProductStores;
import com.vmware.entertainmentetc.services.bestbuy.BestBuyService;
import com.vmware.entertainmentetc.services.mapquest.MapQuestService;

/**
 * Simple little @Controller that invokes Facebook and renders the result.
 * The injected {@link Facebook} reference is configured with the required authorization credentials for the current user behind the scenes.
 * @author Keith Donald
 */
@Controller
public class HomeController {

	private final Facebook facebook;
	
	private final BestBuyService bby;
	private final MapQuestService mq;
	
	@Inject
	public HomeController(Facebook facebook, BestBuyService bestBuyService, MapQuestService mapQuestService) {
		this.facebook = facebook;
		this.bby = bestBuyService;
		this.mq = mapQuestService;
	}
	
	@RequestMapping(value="/movie", method=RequestMethod.GET)
	public String movie(Model model, @RequestParam String movieTitle, @RequestParam String category) throws RemixException {
		List<ProductStores> ps = bby.getProductStores(movieTitle, mq.getLatLngFromCityState(facebook.userOperations().getUserProfile().getLocation().getName()), category);
		model.addAttribute("productStores", ps);
		model.addAttribute("movieTitle", movieTitle);
		return "movie";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws RemixException {

		LikeOperations likes = facebook.likeOperations();
		model.addAttribute("movies", likes.getMovies());
		model.addAttribute("television", likes.getTelevision());
		model.addAttribute("location", facebook.userOperations().getUserProfile().getLocation().getName());

		return "home";
	}

}