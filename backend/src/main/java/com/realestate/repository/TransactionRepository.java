package com.realestate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.model.Transaction;
import com.realestate.model.Transaction.Status;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByStatus(Status status);

	List<Transaction> findByBuyerId(int buyerId);

	List<Transaction> findBySellerId(int sellerId);

	@Query(value = "SELECT seller_id, COUNT(*) as completedCount " + "FROM transaction " + "WHERE status = 'COMPLETED' "
			+ "GROUP BY seller_id " + "ORDER BY completedCount DESC " + "LIMIT 3", nativeQuery = true)
	List<Object[]> findTop3SellerIdsWithCompletedCount();

	@Query("SELECT t.status, COUNT(t) FROM Transaction t GROUP BY t.status")
	List<Object[]> countTransactionsByStatus();

	@Query("SELECT COUNT(t) FROM Transaction t WHERE t.status = 'COMPLETED'")
	long countTotalSales();

	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = 'COMPLETED'")
	double countTotalSalesAmount();

	@Query("SELECT COUNT(t) FROM Transaction t WHERE t.status = 'COMPLETED' AND t.manager IS NOT NULL")
	long countTotalSalesByManager();

	Optional<Transaction> findByBuyerIdAndPropertyId(int buyerId, int propertyId);
}
