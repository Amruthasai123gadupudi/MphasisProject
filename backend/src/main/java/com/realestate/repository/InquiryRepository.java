package com.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.model.Inquiry;
import com.realestate.model.Inquiry.TargetRole;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

	@Query(value = "SELECT * FROM inquiry WHERE target_user_id = :userId", nativeQuery = true)
	List<Inquiry> findInquiriesByTargetUserId(@Param("userId") int userId);

	List<Inquiry> findByBuyer_Id(int buyerId);

	List<Inquiry> findByResponder_Id(int responderId);


}
