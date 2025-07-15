package com.realestate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.model.Transaction;
import com.realestate.model.Transaction.Status;
import com.realestate.model.User;
import com.realestate.service.TransactionService;
import com.realestate.service.UserService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    //seller module
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
   @PostMapping
   public ResponseEntity<Transaction> createTransaction(
        @RequestParam("buyerId") int buyerId,
        @RequestParam("propertyId") int propertyId
    ) {
        Transaction transaction = transactionService.createTransaction(buyerId, propertyId);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
        @PathVariable("id") int id,
        @RequestParam("status") Transaction.Status status
    ) {
        Transaction updated = transactionService.updateTransactionStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable("id") int id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if(transaction.get()!=null)
        {
        return ResponseEntity.ok(transaction.get());
        }
        else
        {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");

        }
    }
    
    //manager module
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Transaction>> getByStatus(@PathVariable("status") Status status) {
        return ResponseEntity.ok(transactionService.getTransactionsByStatus(status));
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Transaction>> getByBuyer(@PathVariable("buyerId") int buyerId) {
        return ResponseEntity.ok(transactionService.getTransactionsByBuyerId(buyerId));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Transaction>> getBySeller(@PathVariable("sellerId") int sellerId) {
        return ResponseEntity.ok(transactionService.getTransactionsBySellerId(sellerId));
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> getUsersByName(@RequestParam("name") String name) {
        List<User> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
}
