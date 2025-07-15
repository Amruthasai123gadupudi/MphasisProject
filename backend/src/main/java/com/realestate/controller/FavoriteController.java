package com.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.model.Favorite;
import com.realestate.model.ViewedProperty;
import com.realestate.service.FavoriteService;
//import com.realestate.service.ViewPropertyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/buyer/favorites")
@RequiredArgsConstructor
public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;

	@PostMapping
	public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite favorite) {
		return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.addToFavorites(favorite));
	}

	@GetMapping("/{buyerId}")
	public ResponseEntity<List<Favorite>> getFavorites(@PathVariable("buyerId") int buyerId) {
		List<Favorite> favorites = favoriteService.getFavoritesByBuyer(buyerId);
		return ResponseEntity.ok(favorites);
	}
	@GetMapping
	public ResponseEntity<List<Favorite>> getFavoritesall() {
		List<Favorite> favorites = favoriteService.getFavoritesByBuyerall();
		return ResponseEntity.ok(favorites);
	}
	@DeleteMapping("/{favoriteId}")
	public ResponseEntity<Void> removeFavorite(@PathVariable("favoriteId") int favoriteId) {
		favoriteService.removeFavorite(favoriteId);
		return ResponseEntity.noContent().build();
	}

}
