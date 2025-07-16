import { NgModule } from "@angular/core";
import { Dashboard3Component } from "./dashboard3.component";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { DashboardComponent } from "../Manager_Module/dashboard/dashboard.component";
import { Inquiry } from "../Manager_Module/model/inquiry";

import { ManagerComponent } from "../Manager_Module/manager/manager.component";
import { TopSellerComponent } from "../Manager_Module/top-seller/top-seller.component";
import { TransactionSearchComponent } from "../Manager_Module/transaction-search/transaction-search.component";
import { Inquiry1Component } from "../Manager_Module/inquiry1/inquiry1.component";

 

 
@NgModule({
  declarations: [
   Dashboard3Component,
   DashboardComponent,
   Inquiry1Component,
   ManagerComponent,
   TopSellerComponent,
   TransactionSearchComponent

   
  ],
  imports: [
    BrowserModule,
    Dashboard3Module,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [Dashboard3Module]
})
export class Dashboard3Module { }