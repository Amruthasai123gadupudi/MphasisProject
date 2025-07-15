package com.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.model.Favorite;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	@Query("select f from Favorite f where f.buyer.id=:buyerId")
    List<Favorite> findByBuyerId(@Param("buyerId") Integer buyerId);
	
    boolean existsByBuyerIdAndPropertyId(Integer buyerId, Integer propertyId);
}