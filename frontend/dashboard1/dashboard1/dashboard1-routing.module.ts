import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'buyer-dashboard', pathMatch: 'full' },
  {
    path: 'buyer-dashboard',
    loadChildren: () =>
      import('../buyer/buyer.module').then(m => m.BuyerModule)
  },
  { path: '**', redirectTo: 'buyer-dashboard' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Dashboard1RoutingModule {}