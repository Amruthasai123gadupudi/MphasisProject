package com.realestate.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.realestate.model.Favorite;
import com.realestate.repository.FavoriteRepository;
import com.realestate.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite addToFavorites(Favorite favorite) {
        boolean exists = favoriteRepository.existsByBuyerIdAndPropertyId(
            favorite.getBuyer().getId(), favorite.getProperty().getId());

        if (exists) {
            throw new RuntimeException("Property is already marked as favorite.");
        }

        return favoriteRepository.save(favorite);
    }

    @Override
    public List<Favorite> getFavoritesByBuyer(Integer buyerId) {
        return favoriteRepository.findByBuyerId(buyerId);
    }

    @Override
    public void removeFavorite(Integer id) {
        favoriteRepository.deleteById(id);
    }

	@Override
	public List<Favorite> getFavoritesByBuyerall() {
		// TODO Auto-generated method stub
		return favoriteRepository.findAll();
	}
}
