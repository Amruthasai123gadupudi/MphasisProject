import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { UserListComponent } from './user-list/user-list.component';
import { ReportComponent } from './report/report.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PropertyCategoriesComponent } from './property-categories/property-categories.component';
import { RulesViewComponent } from './rules-view/rules-view.component';
import { NotificationComponent } from './notification/notification.component';

@NgModule({
  declarations: [
    UserListComponent,
    ReportComponent,
    DashboardComponent,
    PropertyCategoriesComponent,
    RulesViewComponent,
    RulesViewComponent,
    NotificationComponent  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AdminModule {}
