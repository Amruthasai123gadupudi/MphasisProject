import { Component, OnInit } from '@angular/core';
import { PropertyService } from 'src/app/buyer/services/property.service';
import { Property } from '../models/property.model';
import { Favorite } from 'src/app/buyer/models/favorite.model';
import { HttpClient } from '@angular/common/http';
import { ViewedPropertyService } from 'src/app/buyer/services/viewed-property.service';
import { User } from '../models/user';

declare var bootstrap: any;

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {

  properties: Property[] = [];
  user: User = new User();

  constructor(
    private propertyService: PropertyService,
    private viewedPropertyService: ViewedPropertyService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    // Load approved properties
    this.propertyService.getAllApproved().subscribe({
      next: (data) => this.properties = data,
      error: (err) => console.error('Error loading properties', err)
    });

    // Load logged-in user
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.user = JSON.parse(userJson) as User;
    } else {
      console.warn('User not found in localStorage');
    }
  }

  sendQuickInquiry(property: Property): void {
    const buyerId = this.user.id;
    const message = property.message?.trim();
    const targetRole = property.targetRole;

    if (!message) {
      alert('Please enter a message.');
      return;
    }

    if (!targetRole || (targetRole !== 'SELLER' && targetRole !== 'MANAGER')) {
      alert('Please select a valid target role.');
      return;
    }

    this.propertyService.sendInquiry({
      propertyId: property.id,
      buyerId,
      message,
      targetRole
    }).subscribe({
      next: () => {
        alert('Inquiry sent!');
        property.message = '';
        property.targetRole = 'SELLER'; // reset default
      },
      error: (err) => {
        console.error('Error sending inquiry', err);
        alert('Failed to send inquiry.');
      }
    });
  }

  search = {
    location: '',
    minPrice: undefined,
    maxPrice: undefined,
    typeId: undefined
  };

  propertyTypes = [
    { id: 1, name: 'Apartment' },
    { id: 2, name: 'Villa' },
    { id: 3, name: 'Independent House' },
    { id: 4, name: 'Plot' }
  ];

  onSearch(): void {
    this.propertyService.searchProperties(
      this.search.location || undefined,
      this.search.minPrice,
      this.search.maxPrice,
      this.search.typeId
    ).subscribe({
      next: (data) => this.properties = data,
      error: (err) => console.error('Search failed', err)
    });
  }

  onReset(): void {
    this.search = {
      location: '',
      minPrice: undefined,
      maxPrice: undefined,
      typeId: undefined
    };
    this.onSearch();
  }

  toggleInquiry(property: Property): void {
    property.showInquiryForm = !property.showInquiryForm;
  }

  toggleFavorite(property: Property): void {
    property.isFavorite = !property.isFavorite; // toggle heart UI immediately

    const favorite: Favorite = {
      buyer: { id: this.user.id },
      property: { id: property.id }
    };

    if (property.isFavorite) {
      this.http.post<Favorite>('http://localhost:8082/api/buyer/favorites', favorite)
        .subscribe({
          next: () => console.log('✅ Added to favorites'),
          error: err => {
            console.error(err);
            property.isFavorite = false; // rollback UI
          }
        });
    } else {
      this.http.delete(`http://localhost:8082/api/buyer/favorites/remove/${property.id}`)
        .subscribe({
          next: () => console.log('❌ Removed from favorites'),
          error: err => {
            console.error(err);
            property.isFavorite = true; // rollback UI
          }
        });
    }
  }

  selectedPropertyId!: number;

  openInquiryModal(propertyId: number): void {
    this.selectedPropertyId = propertyId;
    const modalEl = document.getElementById('inquiryModal');
    if (modalEl) {
      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    }
  }

  viewProperty(property: Property): void {
    const buyerId = this.user.id;

    const payload = {
      buyer: { id: buyerId },
      property: { id: property.id }
    };

    this.viewedPropertyService.logViewedProperty(payload).subscribe({
      next: () => console.log('👁️ Viewed property logged'),
      error: err => console.error('Error logging view:', err)
    });
  }

  selectedProperty!: Property;

  openPropertyModal(property: Property): void {
    this.selectedProperty = property;
    const modalEl = document.getElementById('propertyModal');
    if (modalEl) {
      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    }
  }

  buyProperty(): void {
    const buyerId = this.user.id;
    const property = this.selectedProperty;

    if (!property) {
      console.warn('⚠️ No property selected');
      return;
    }

    this.http.post(`http://localhost:8082/api/transactions`, null, {
      params: {
        buyerId: buyerId.toString(),
        propertyId: property.id.toString()
      }
    }).subscribe({
      next: () => {
        alert('✅ Property purchase transaction created!');
        const modalEl = document.getElementById('propertyModal');
        if (modalEl) bootstrap.Modal.getInstance(modalEl)?.hide();
      },
      error: (err) => {
        if (err.status === 409) {
          alert('⚠️ You have already purchased this property.');
        } else {
          console.error('❌ Failed to create transaction:', err);
          alert('Something went wrong while processing the purchase.');
        }
      }
    });
  }
}
