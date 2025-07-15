package com.realestate.service;

import java.util.List;

import com.realestate.model.Favorite;

public interface FavoriteService  {
    abstract Favorite addToFavorites(Favorite favorite);
    abstract List<Favorite> getFavoritesByBuyer(Integer buyerId);
    abstract void removeFavorite(Integer id);
	abstract List<Favorite> getFavoritesByBuyerall();
}