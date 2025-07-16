// src/app/admin/admin-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { UserListComponent } from './user-list/user-list.component';
import { ReportComponent } from './report/report.component';
import { PlatformConfigService } from './services/platform-config.service';
import { PropertyCategoriesComponent } from './property-categories/property-categories.component';
import { RulesViewComponent } from './rules-view/rules-view.component';
import { NotificationComponent } from './notification/notification.component';
const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'users', pathMatch: 'full' },
      { path: 'users', component: UserListComponent },
      { path: 'users/add', component: UserListComponent },
      { path: 'reports', component: ReportComponent },
      { path: 'categories', component: PropertyCategoriesComponent },
      { path: 'rules', component: RulesViewComponent },
      { path: 'notifications', component: NotificationComponent }
    ]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
