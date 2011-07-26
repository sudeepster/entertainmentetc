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

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.LikeOperations;
import org.springframework.social.facebook.api.UserLike;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mattwilliamsnyc.service.remix.RemixException;
import com.mattwilliamsnyc.service.remix.Store;
import com.mattwilliamsnyc.service.remix.StoresResponse;
import com.vmware.entertainmentetc.services.bestbuy.BestBuyService;

/**
 * Simple little @Controller that invokes Facebook and renders the result.
 * The injected {@link Facebook} reference is configured with the required authorization credentials for the current user behind the scenes.
 * @author Keith Donald
 */
@Controller
public class HomeController {

	private final Facebook facebook;
	
	// TODO: fix service invocation
	private final BestBuyService bby;
	
	@Inject
	public HomeController(Facebook facebook) {
		this.facebook = facebook;
		this.bby = new BestBuyService();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws RemixException {
//		List<Reference> friends = facebook.friendOperations().getFriends();
//		model.addAttribute("friends", friends);
		LikeOperations likes = facebook.likeOperations();
		model.addAttribute("music", likes.getMusic());
		model.addAttribute("books", likes.getBooks());
		model.addAttribute("movies", likes.getMovies());
		model.addAttribute("television", likes.getTelevision());
		
		// getMovies returns a list of UserLike objects
		// conveniently, we can pick one and see if it's available
		if (!likes.getMovies().isEmpty()) {
			UserLike aMovie = likes.getMovies().get(0);
			// TODO: find the user's actual zip code
			StoresResponse stores = bby.getProductInNearbyStores(aMovie.getName(), "94304");
			model.addAttribute("movieName", aMovie.getName());
			
			
			// Pick a store
			if ((stores != null) && (!stores.list().isEmpty())) {
				Store aStore = stores.list().get(0);
				model.addAttribute("products", aStore.getProducts());
			}
		}
		return "home";
	}

}