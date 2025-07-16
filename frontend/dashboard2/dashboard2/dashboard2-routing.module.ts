import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PropertyComponent } from '../seller/property/property.component';
import { SellerTransactionsComponent } from '../seller/seller-transactions/seller-transactions.component';

 
const routes: Routes = [
  // {
  //   path: 'seller', loadChildren:()=>import('./seller/seller.module').then(m=>m.SellerModule)
  // },
  // { path: '', redirectTo: 'seller', pathMatch: 'full' },
  // { path: '**', redirectTo: 'seller' }
  // { path: 'inquiries', component: InquiryComponent },
  { path: '', redirectTo: '/properties', pathMatch: 'full' },  // redirect to inquiries
  { path: 'properties', component: PropertyComponent },
  // { path: '**', redirectTo: '/inquiries' },
  // { path: 'transactions', component: SellerTransactionsComponent }, // wildcard fallback to inquiries
];
 
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Dashboard2RoutingModule { }