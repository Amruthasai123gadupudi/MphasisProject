 
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Dashboard2Component } from './dashboard2.component';
import { PropertyComponent } from '../seller/property/property.component';

import { SellerTransactionsComponent } from '../seller/seller-transactions/seller-transactions.component';
import { InquiryComponent } from '../seller/inquiry/inquiry.component';
 

 
@NgModule({
  declarations: [
   Dashboard2Component,
   PropertyComponent,
   InquiryComponent,
   SellerTransactionsComponent,

   
  ],
  imports: [
    BrowserModule,
    Dashboard2Module,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [Dashboard2Module]
})
export class Dashboard2Module { }