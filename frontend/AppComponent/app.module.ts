import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { Dashboard1Component } from './dashboard1/dashboard1.component';
import { Dashboard2Component } from './dashboard2/dashboard2.component';
import { Dashboard3Component } from './dashboard3/dashboard3.component';
import { Dashboard4Component } from './dashboard4/dashboard4.component';
import { PropertyComponent } from './seller/property/property.component';

import { SellerTransactionsComponent } from './seller/seller-transactions/seller-transactions.component';
import { InquiryComponent } from './seller/inquiry/inquiry.component';
import { DashboardComponent } from './Manager_Module/dashboard/dashboard.component';
import { ManagerComponent } from './Manager_Module/manager/manager.component';
import { TopSellerComponent } from './Manager_Module/top-seller/top-seller.component';
import { TransactionSearchComponent } from './Manager_Module/transaction-search/transaction-search.component';
import { Inquiry1Component } from './Manager_Module/inquiry1/inquiry1.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    Dashboard1Component,
    Dashboard2Component,
    Dashboard3Component,
    Dashboard4Component,
    PropertyComponent,
       InquiryComponent,
       SellerTransactionsComponent,
       DashboardComponent,
        Inquiry1Component,
          ManagerComponent,
          TopSellerComponent,
          TransactionSearchComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
