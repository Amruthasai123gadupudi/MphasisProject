package com.realestate.controller;
 
import com.realestate.model.Transaction;
import com.realestate.model.Transaction.Status;
import com.realestate.model.User;
import com.realestate.service.TransactionService;
import com.realestate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
 
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private TransactionService transactionService;
 
    @MockBean
    private UserService userService;
 
    private Transaction sampleTransaction;
    private User buyer;
    private User seller;
 
    @BeforeEach
    public void setup() {
        buyer = new User();
        buyer.setId(1);
        buyer.setName("Alice");
 
        seller = new User();
        seller.setId(2);
        seller.setName("Bob");
 
        sampleTransaction = new Transaction();
        sampleTransaction.setId(100);
        sampleTransaction.setBuyer(buyer);
        sampleTransaction.setSeller(seller);
        sampleTransaction.setStatus(Status.INITIATED);
        sampleTransaction.setClosedAt(LocalDateTime.now());
    }
 
    @Test
    public void testGetAllTransactions() throws Exception {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(List.of(sampleTransaction));
 
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("INITIATED"));
    }
 
    @Test
    public void testGetTransactionsByStatus() throws Exception {
        Mockito.when(transactionService.getTransactionsByStatus(Status.IN_PROGRESS))
                .thenReturn(List.of(sampleTransaction));
 
        mockMvc.perform(get("/api/transactions/status/IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
 
    @Test
    public void testGetTransactionsByBuyer() throws Exception {
        Mockito.when(transactionService.getTransactionsByBuyerId(1))
                .thenReturn(List.of(sampleTransaction));
 
        mockMvc.perform(get("/api/transactions/buyer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
 
    @Test
    public void testGetTransactionsBySeller() throws Exception {
        Mockito.when(transactionService.getTransactionsBySellerId(2))
                .thenReturn(List.of(sampleTransaction));
 
        mockMvc.perform(get("/api/transactions/seller/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
 
    @Test
    public void testSearchUsersByName() throws Exception {
        Mockito.when(userService.searchUsersByName(anyString()))
                .thenReturn(Arrays.asList(buyer, seller));
 
        mockMvc.perform(get("/api/transactions/search?name=ali"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }
}