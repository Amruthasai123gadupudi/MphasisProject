import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PropertyComponent } from '../seller/property/property.component';
import { SellerTransactionsComponent } from '../seller/seller-transactions/seller-transactions.component';
import { ManagerComponent } from '../Manager_Module/manager/manager.component';
import { TopSellerComponent } from '../Manager_Module/top-seller/top-seller.component';
import { TransactionSearchComponent } from '../Manager_Module/transaction-search/transaction-search.component';
import { Inquiry1Component } from '../Manager_Module/inquiry1/inquiry1.component';


 
const routes: Routes = [
    {
      path: 'manager-dashboard',
      children: [
        { path: '', component:ManagerComponent },
        { path: 'top-seller', component: TopSellerComponent },
        { path: 'transactions', component: TransactionSearchComponent },
        //{ path: 'properties', component: ManagerComponent },
        { path: 'inquiries', component: Inquiry1Component }
      ]
    },
    { path: '', redirectTo: 'manager-dashboard', pathMatch: 'full' },
  ];
 
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Dashboard2RoutingModule { }