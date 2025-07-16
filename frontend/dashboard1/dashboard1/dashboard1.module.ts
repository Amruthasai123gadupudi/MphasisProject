import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { Dashboard1Component } from './dashboard1.component';
import { Dashboard1RoutingModule } from './dashboard1-routing.module';

@NgModule({
  declarations: [
    Dashboard1Component
  ],
  imports: [
    BrowserModule,
    Dashboard1RoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [Dashboard1Component]
})
export class Dashboard1Module {}
