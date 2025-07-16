import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction';
// adjust path as needed
// optional enum for status if you have
 
@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  private apiUrl = 'http://localhost:8082/api/transactions';
 
  constructor(private http: HttpClient) {}
 
  // Create a new transaction
  initiateTransaction(buyerId: number, propertyId: number): Observable<Transaction> {
    const params = new HttpParams()
      .set('buyerId', buyerId.toString())
      .set('propertyId', propertyId.toString());
 
    return this.http.post<Transaction>(this.apiUrl, null, { params });
  }
 
  // Update status of a transaction
  updateTransactionStatus(id: number, status: string): Observable<Transaction> {
    const params = new HttpParams().set('status', status);
    return this.http.put<Transaction>(`${this.apiUrl}/${id}/status`, null, { params });
  }
 
  // Get all transactions
  getAllTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.apiUrl);
  }
 
  // Get transaction by id
  getTransactionById(id: number): Observable<Transaction> {
    return this.http.get<Transaction>(`${this.apiUrl}/${id}`);
  }
 
  // Get transactions by buyerId
  getTransactionsByBuyerId(buyerId: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/buyer/${buyerId}`);
  }
 
  // Get transactions by sellerId
  getTransactionsBySellerId(sellerId: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/seller/${sellerId}`);
  }
 
  // Get transactions by status
  getTransactionsByStatus(status: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/status/${status}`);
  }
}