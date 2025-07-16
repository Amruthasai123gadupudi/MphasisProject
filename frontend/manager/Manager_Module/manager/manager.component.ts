import { Component, OnInit } from '@angular/core';
import { Property } from '../model/property';
import { PropertyType } from '../model/property-type';
import { ManagerService } from '../services/manager.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {
  properties: Property[] = [];
  allProperties: Property[] = [];
  showPending = false;
  showApproved = false;
  editingProperty?: Property;
  errorMessage: string = '';

  managerIdFilter: number = 0;

  propertyTypes: PropertyType[] = [
    { id: 1, name: 'Apartment' },
    { id: 2, name: 'House' },
    { id: 3, name: 'Villa' },
    { id: 4, name: 'Commercial' }
  ];

  constructor(private managerService: ManagerService) {}

  ngOnInit(): void {
    // Retrieve manager ID from localStorage (user object)
    const userJson = localStorage.getItem('user');
    if (userJson) {
      try {
        const user = JSON.parse(userJson);
        if (user && user.id) {
          this.managerIdFilter = user.id;
        }
      } catch {
        this.managerIdFilter = 0;
      }
    }

    this.managerService.getCombined().subscribe((data: { approved: Property[], pending: Property[] }) => {
      const all = [...data.approved, ...data.pending];
      this.allProperties = all.filter(p => p.manager?.id === this.managerIdFilter);
      this.properties = [...this.allProperties];
    });
  }

  toggleFilter(type: 'approved' | 'pending') {
    if (type === 'pending') {
      this.showPending = !this.showPending;
      if (this.showPending) this.showApproved = false;
    } else if (type === 'approved') {
      this.showApproved = !this.showApproved;
      if (this.showApproved) this.showPending = false;
    }
    this.filterProperties();
  }

  filterProperties() {
    if (!this.showApproved && !this.showPending) {
      this.properties = [...this.allProperties];
    } else if (this.showApproved) {
      this.properties = this.allProperties.filter(p => p.approved);
    } else if (this.showPending) {
      this.properties = this.allProperties.filter(p => !p.approved);
    }
  }

  approve(propertyId: number) {
    this.managerService.approveProperty(propertyId).subscribe(() => {
      this.refreshProperties();
    });
  }

  delete(propertyId: number): void {
    this.managerService.deleteProperty(propertyId).subscribe({
      next: (message) => {
        alert(message);
        this.refreshProperties();
      },
      error: (error) => {
        console.error('Delete failed:', error);
        this.errorMessage = 'Failed to delete property.';
      }
    });
  }

  startEdit(property: Property) {
    this.editingProperty = { ...property };
  }

  submitEdit(form: NgForm) {
    if (!this.editingProperty) return;

    this.managerService.editProperty(this.editingProperty.id, this.editingProperty)
      .subscribe(() => {
        this.refreshProperties();
        this.editingProperty = undefined;
      });
  }

  cancelEdit() {
    this.editingProperty = undefined;
  }

  private refreshProperties() {
    this.managerService.getCombined().subscribe((data: { approved: Property[], pending: Property[] }) => {
      const all = [...data.approved, ...data.pending];
      this.allProperties = all.filter(p => p.manager?.id === this.managerIdFilter);
      this.filterProperties();
    });
  }
}
