package com.realestate.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
 
import com.realestate.model.Transaction;

import com.realestate.model.Transaction.Status;

import com.realestate.model.User;

import com.realestate.service.TransactionService;

import com.realestate.service.UserService;
 
@CrossOrigin(origins = "http://localhost:4200")

@RestController

@RequestMapping("/api/transactions")

public class TransactionController {
 
    @Autowired

    private TransactionService transactionService;

    @Autowired

    private UserService userService;
 
//    @PostMapping("/create")

//    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {

//        return ResponseEntity.ok(transactionService.createTransaction(transaction));

//    }

//

//    @PutMapping("/update-status/{id}")

//    public ResponseEntity<Transaction> updateStatus(

//            @PathVariable int id,

//            @RequestParam Status status) {

//        return ResponseEntity.ok(transactionService.updateTransactionStatus(id, status));

//    }
 
    @GetMapping

    public ResponseEntity<List<Transaction>> getAllTransactions() {

        return ResponseEntity.ok(transactionService.getAllTransactions());

    }
 
    @GetMapping("/status/{status}")

    public ResponseEntity<List<Transaction>> getByStatus(@PathVariable Status status) {

        return ResponseEntity.ok(transactionService.getTransactionsByStatus(status));

    }
 
    @GetMapping("/buyer/{buyerId}")

    public ResponseEntity<List<Transaction>> getByBuyer(@PathVariable int buyerId) {

        return ResponseEntity.ok(transactionService.getTransactionsByBuyerId(buyerId));

    }
 
    @GetMapping("/seller/{sellerId}")

    public ResponseEntity<List<Transaction>> getBySeller(@PathVariable int sellerId) {

        return ResponseEntity.ok(transactionService.getTransactionsBySellerId(sellerId));

    }

    //added

    @GetMapping("/search")

    public ResponseEntity<List<User>> getUsersByName(@RequestParam String name) {

        List<User> users = userService.searchUsersByName(name);

        return ResponseEntity.ok(users);

    }
 
}

 