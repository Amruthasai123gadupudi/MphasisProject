import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BuyerDashboardComponent } from './dashboard/buyer-dashboard.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { InquiryListComponent } from './inquiries/inquiry-list/inquiry-list.component';
import { ViewedComponent } from './viewed/viewed.component';
import { TransactionsComponent } from './transactions/transactions.component';

const routes: Routes = [
  {
    path: '',
    component: BuyerDashboardComponent,
    children: [
      { path: '', component: PropertyListComponent },
      { path: 'favorites', component: FavoritesComponent },
      { path: 'inquiries', component: InquiryListComponent },
      { path: 'viewed', component: ViewedComponent },
      { path: 'transactions', component: TransactionsComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BuyerRoutingModule {}
