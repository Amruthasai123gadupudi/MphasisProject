package com.realestate.service;

import java.util.List;
import java.util.Optional;

import com.realestate.model.Transaction;
import com.realestate.model.Transaction.Status;

public interface TransactionService {
abstract Transaction createTransaction(int buyerId, int propertyId);
abstract Transaction updateTransactionStatus(int transactionId, Transaction.Status status);
abstract List<Transaction> getAllTransactions();
abstract Optional<Transaction> getTransactionById(int id);
List<Transaction> getTransactionsByStatus(Status status);
List<Transaction> getTransactionsByBuyerId(int buyerId);
List<Transaction> getTransactionsBySellerId(int sellerId);
}
