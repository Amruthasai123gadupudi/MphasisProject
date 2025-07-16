import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { Dashboard1Component } from './dashboard1/dashboard1.component';
import { Dashboard2Component } from './dashboard2/dashboard2.component';
import { Dashboard3Component } from './dashboard3/dashboard3.component';
import { Dashboard4Component } from './dashboard4/dashboard4.component';
import { RoleGuard } from './auth.guard';
import { HomeComponent } from './home/home.component';
import { PropertyComponent } from './seller/property/property.component';
import { ManagerComponent } from './Manager_Module/manager/manager.component';
import { TopSellerComponent } from './Manager_Module/top-seller/top-seller.component';
import { TransactionSearchComponent } from './Manager_Module/transaction-search/transaction-search.component';
import { Inquiry1Component } from './Manager_Module/inquiry1/inquiry1.component';

//import { RulesComponent } from './rules/rules.component';

 // Make sure the path is correct

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },

  {
    path: 'dashboard-buyer',
    component: Dashboard1Component,
    canActivate: [RoleGuard],
    data: { role: 'BUYER' },
    children: [
      {
        path: '',
        redirectTo: 'buyer-dashboard',
        pathMatch: 'full'
      },
      {
        path: 'buyer-dashboard',
        loadChildren: () =>
          import('./buyer/buyer.module').then(m => m.BuyerModule)
      }
    ]
  },
  {
    path: 'dashboard-seller',
    component: Dashboard2Component,
    canActivate: [RoleGuard],
    data: { role: 'SELLER' },
    children: [
       { path: '', redirectTo: 'properties', pathMatch: 'full' },  // redirect to inquiries
        { path: 'properties', component: PropertyComponent },
    ]
  },
  {
    path: 'dashboard-manager',
    component: Dashboard3Component,
    canActivate: [RoleGuard],
    data: { role: 'MANAGER' },
    children: [
      {
            path: 'manager-dashboard',
            children: [
              { path: '', component:ManagerComponent },
              { path: 'top-seller', component: TopSellerComponent },
              { path: 'transactions', component: TransactionSearchComponent },
              { path: 'properties', component: ManagerComponent },
              { path: 'inquiries', component: Inquiry1Component }
            ]
          },
          { path: '', redirectTo: 'manager-dashboard', pathMatch: 'full' },
   ]
  },
  {
    path: 'dashboard-admin',
    component: Dashboard4Component,
    canActivate: [RoleGuard],
    data: { role: 'ADMIN' },
    children:[
      { path: '', redirectTo: 'admin', pathMatch: 'full' },
      {
        path: 'admin',
        loadChildren: () =>
          import('./admin/admin.module').then(m => m.AdminModule)
      }
    ]
    
  },
  {
    path: 'home',
    component: HomeComponent,
    
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }