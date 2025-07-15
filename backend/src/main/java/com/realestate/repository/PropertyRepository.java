package com.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.model.Property;
import com.realestate.model.User;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

	// Dynamic filter: all fields optional
	@Query("SELECT p FROM Property p WHERE " + "(:location IS NULL OR p.location LIKE %:location%) AND "
			+ "(:minPrice IS NULL OR p.price >= :minPrice) AND "
			+ "(:maxPrice IS NULL OR p.price <= :maxPrice) AND "
			+ "(:typeId IS NULL OR p.type.id = :typeId) AND " 
			+ "p.approved = true")
	List<Property> searchProperties(@Param("location") String location, @Param("minPrice") Double minPrice,
			@Param("maxPrice") Double maxPrice, @Param("typeId") Integer typeId);
	
	List<Property> findByApprovedTrue();
	

	@Query("SELECT p FROM Property p WHERE p.id NOT IN ("
			+ "SELECT t.property.id FROM Transaction t WHERE t.status = com.realestate.model.Transaction.Status.COMPLETED"
			+ ")")
	List<Property> findPropertiesWithoutCompletedTransactions();

	@Query("SELECT p.approved, COUNT(p) FROM Property p GROUP BY p.approved")
	List<Object[]> countPropertiesByApproval();

	@Query("SELECT COUNT(p) FROM Property p WHERE p.approved = true")
	long countApprovedProperties();

	@Query("SELECT COUNT(p) FROM Property p")
	long countTotalProperties();

	

	List<Property> findByApprovedFalse();

	List<Property> findBySeller(User seller);
	

@Query("""
		    SELECT p FROM Property p
		    WHERE p.id IN (
		        SELECT MIN(p2.id) FROM Property p2
		        WHERE p2.approved = true AND
		            NOT EXISTS (
		                SELECT t FROM Transaction t
		                WHERE t.property = p2 AND t.status = 'COMPLETED'
		            )
		        GROUP BY p2.title, p2.description, p2.price, p2.location
		    )
		""")
		List<Property> findApprovedPropertiesWithIncompleteTransactions();

}
