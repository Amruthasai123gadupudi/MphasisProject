import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { BuyerDashboardComponent } from './dashboard/buyer-dashboard.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { ViewedComponent } from './viewed/viewed.component';
import { TransactionsComponent } from './transactions/transactions.component';


import { BuyerRoutingModule } from './buyer-routing.module';
import { InquiriesModule } from './inquiries/inquiries.module';

@NgModule({
  declarations: [
    BuyerDashboardComponent,
    PropertyListComponent,
    FavoritesComponent,
    ViewedComponent,
    TransactionsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    BuyerRoutingModule,
    InquiriesModule
  ]
})
export class BuyerModule {}
