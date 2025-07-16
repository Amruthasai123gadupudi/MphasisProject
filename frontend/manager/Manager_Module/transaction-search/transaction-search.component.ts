import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../services/transaction.service';
 
 
@Component({
  selector: 'app-transaction-search',
  templateUrl: './transaction-search.component.html',
  styleUrls: ['./transaction-search.component.css']
})
export class TransactionSearchComponent implements OnInit {
  searchType: string = 'all'; // 👈 Show all by default
  query: string = '';
  results: any[] = [];
  errorMessage: string = '';
 
  constructor(private transactionService: TransactionService) {}
 
  ngOnInit(): void {
    this.getAllTransactions(); // 👈 Load all transactions on component load
  }
 
  getAllTransactions(): void {
    this.transactionService.getAllTransactions().subscribe({
      next: data => this.results = data,
      error: () => this.errorMessage = 'Could not fetch all transactions.'
    });
  }
 
  search(): void {
    this.errorMessage = '';
    this.results = [];
 
    switch (this.searchType) {
      case 'sellerId':
        this.transactionService.getBySellerId(Number(this.query)).subscribe({
          next: data => this.results = data,
          error: () => this.errorMessage = 'Could not fetch transactions by seller ID.'
        });
        break;
      case 'buyerId':
        this.transactionService.getByBuyerId(Number(this.query)).subscribe({
          next: data => this.results = data,
          error: () => this.errorMessage = 'Could not fetch transactions by buyer ID.'
        });
        break;
      case 'status':
        this.transactionService.getByStatus(this.query.toUpperCase()).subscribe({
          next: data => this.results = data,
          error: () => this.errorMessage = 'Invalid or unsupported status.'
        });
        break;
      case 'name':
        this.transactionService.searchByName(this.query).subscribe({
          next: data => this.results = data,
          error: () => this.errorMessage = 'Could not search by name.'
        });
        break;
      case 'all':
        this.getAllTransactions();
        break;
    }
  }
}




// import { Component, OnInit } from '@angular/core';
// import { TransactionService } from '../services/transaction.service';


// @Component({
//   selector: 'app-transaction-search',
//   templateUrl: './transaction-search.component.html',
//   styleUrls: ['./transaction-search.component.css']
// })
// export class TransactionSearchComponent implements OnInit {
//   searchType: string = 'all'; // 👈 Show all by default
//   query: string = '';
//   results: any[] = [];
//   errorMessage: string = '';

//   constructor(private transactionService: TransactionService) {}

//   ngOnInit(): void {
//     this.getAllTransactions(); // 👈 Load all transactions on component load
//   }

//   getAllTransactions(): void {
//     this.transactionService.getAllTransactions().subscribe({
//       next: data => this.results = data,
//       error: () => this.errorMessage = 'Could not fetch all transactions.'
//     });
//   }

//   search(): void {
//     this.errorMessage = '';
//     this.results = [];

//     switch (this.searchType) {
//       case 'sellerId':
//         this.transactionService.getBySellerId(Number(this.query)).subscribe({
//           next: data => this.results = data,
//           error: () => this.errorMessage = 'Could not fetch transactions by seller ID.'
//         });
//         break;
//       case 'buyerId':
//         this.transactionService.getByBuyerId(Number(this.query)).subscribe({
//           next: data => this.results = data,
//           error: () => this.errorMessage = 'Could not fetch transactions by buyer ID.'
//         });
//         break;
//       case 'status':
//         this.transactionService.getByStatus(this.query.toUpperCase()).subscribe({
//           next: data => this.results = data,
//           error: () => this.errorMessage = 'Invalid or unsupported status.'
//         });
//         break;
//       case 'name':
//         this.transactionService.searchByName(this.query).subscribe({
//           next: data => this.results = data,
//           error: () => this.errorMessage = 'Could not search by name.'
//         });
//         break;
//       case 'all':
//         this.getAllTransactions();
//         break;
//     }
//   }
// }
