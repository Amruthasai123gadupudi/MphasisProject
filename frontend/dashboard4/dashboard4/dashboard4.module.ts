import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Dashboard4Component } from './dashboard4.component';
import { Dashboard1Component } from '../dashboard1/dashboard1.component';
import { Dashboard4RoutingModule } from './dashboard4-routing.module';



@NgModule({
  declarations: [
    Dashboard4Component
  ],
  imports: [
    BrowserModule,
    Dashboard4RoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [Dashboard4Component]
})
export class Dashboard4Module {}
