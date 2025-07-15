package com.realestate.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.realestate.model.Property;
import com.realestate.model.Transaction;
import com.realestate.model.Transaction.Status;
import com.realestate.model.User;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.TransactionRepository;
import com.realestate.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public Transaction createTransaction(int buyerId, int propertyId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
 
        // Check if transaction already exists
        Optional<Transaction> existingTransaction = transactionRepository
                .findByBuyerIdAndPropertyId(buyerId, propertyId);
 
        if (existingTransaction.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, 
                "Transaction already exists for this buyer and property."
            );
        }

 
        // Create new transaction
        Transaction transaction = new Transaction();
        transaction.setBuyer(buyer);
        transaction.setSeller(property.getSeller());
        transaction.setProperty(property);
        transaction.setStatus(Transaction.Status.INITIATED);
 
        return transactionRepository.save(transaction);
    }
 
    public Transaction updateTransactionStatus(int transactionId, Transaction.Status status) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setStatus(status);
        if (status == Transaction.Status.COMPLETED) {
            transaction.setClosedAt(LocalDateTime.now());
        }

        return transactionRepository.save(transaction);
    }

	@Override
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}

	@Override
	public Optional<Transaction> getTransactionById(int id) {
		// TODO Auto-generated method stub
		return transactionRepository.findById(id);
	}
	 @Override
	    public List<Transaction> getTransactionsByStatus(Status status) {
	        return transactionRepository.findByStatus(status);
	    }

	    @Override
	    public List<Transaction> getTransactionsByBuyerId(int buyerId) {
	        return transactionRepository.findByBuyerId(buyerId);
	    }

	    @Override
	    public List<Transaction> getTransactionsBySellerId(int sellerId) {
	        return transactionRepository.findBySellerId(sellerId);
	    }
}
