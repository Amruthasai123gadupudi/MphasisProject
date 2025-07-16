import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InquiryFormComponent } from './inquiry-form/inquiry-form.component';
import { InquiryListComponent } from './inquiry-list/inquiry-list.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    InquiryFormComponent,
    InquiryListComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    InquiryFormComponent,
    InquiryListComponent
  ]
})
export class InquiriesModule {}
