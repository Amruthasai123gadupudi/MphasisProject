import { Component, OnInit } from '@angular/core';
import { Transaction } from '../model/transaction';
import { TransactionService } from '../service/transaction.service';
import { Router } from '@angular/router';
import { Inquiry } from '../model/inquiry';

@Component({
  selector: 'app-seller-transactions',
  templateUrl: './seller-transactions.component.html',
  styleUrls: ['./seller-transactions.component.css']
})
export class SellerTransactionsComponent implements OnInit {
  transactions: Transaction[] = [];
  
  loading = true;
  errorMessage = '';
  statusOptions = ['INITIATED', 'IN_PROGRESS', 'COMPLETED'];
  statusUpdates: { [id: number]: string } = {};  // track selected status per txn

  sellerId: number = 2; // fallback default

  constructor(
    private transactionService: TransactionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get sellerId dynamically from localStorage
    const userJson = localStorage.getItem('user');
    if (userJson) {
      try {
        const user = JSON.parse(userJson);
        if (user && user.id) {
          this.sellerId = user.id;
        }
      } catch {
        // fallback to default sellerId=2 if error
      }
    }

    this.transactionService.getTransactionsBySellerId(this.sellerId).subscribe({
      next: (data) => {
        this.transactions = data;
        this.loading = false;

        // Initialize each transaction’s status in statusUpdates
        this.transactions.forEach(txn => {
          this.statusUpdates[txn.id] = txn.status;
        });
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch transactions.';
        this.loading = false;
        console.error(err);
      }
    });

    console.log(JSON.stringify(this.transactions));
  }

  updateStatus(transactionId: number) {
    const newStatus = this.statusUpdates[transactionId];
    this.transactionService.updateTransactionStatus(transactionId, newStatus).subscribe({
      next: (updatedTxn) => {
        const index = this.transactions.findIndex(t => t.id === transactionId);
        if (index > -1) this.transactions[index] = updatedTxn;
        alert(`Transaction status updated to ${updatedTxn.status}`);
      },
      error: (err) => {
        alert('Failed to update status.');
        console.error(err);
      }
    });
  }
}
