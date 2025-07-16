import { Component, ElementRef, ViewChild } from '@angular/core';
import { Property } from '../model/property';
import { PropertyService } from '../service/property.service';
import { User } from '../model/user';
import { PropertyType } from '../model/property-type';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent {
  properties: Property[] = [];
  selectedProperty?: Property;
  errorMessage: string = '';

  viewMode: 'list' | 'add' | 'inquiries' | 'transactions' = 'list';
  selectedFile?: File;

  sellerId: number = 2; // default fallback sellerId
  sellerName: string = 'User';
  constructor(private propertyService: PropertyService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // Get sellerId from localStorage 'user' object dynamically
    const userJson = localStorage.getItem('user');
    if (userJson) {
      try {
        const user = JSON.parse(userJson);
        if (user && user.id) {
          this.sellerId = user.id;
          this.sellerName = user.name || 'User';
        }
      } catch {
        // if error, keep default sellerId = 2
      }
    }
    this.loadPropertiesBySeller(this.sellerId);
  }

  @ViewChild('selectedPropertyCard') selectedPropertyCard!: ElementRef;
  private scrollToSelected: boolean = false;

  // Default seller ID set dynamically here
  newProperty: Property = {
    id: 0,
    title: '',
    description: '',
    price: 0,
    location: '',
    imageUrl: '',
    approved: false,
    seller: {
      id: this.sellerId, // dynamically assigned
      name: '',
      email: '',
      role: 'SELLER',
      active: false
    } as User,
    manager: {
      id: 0,
      name: '',
      email: '',
      role: 'MANAGER',
      active: false
    } as User,
    type: {
      id: 0,
      name: ''
    } as PropertyType,
    createdAt: new Date().toISOString()
  };

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  loadPropertiesBySeller(sellerId: number): void {
    this.propertyService.getPropertiesBySeller(sellerId).subscribe({
      next: (data) => {
        this.properties = data;
        this.errorMessage = '';
        this.selectedProperty = undefined;
        this.viewMode = 'list';
      },
      error: (error) => {
        this.errorMessage = 'Failed to load properties for seller.';
        console.error('Error loading properties:', error);
      }
    });
  }

  loadAvailableProperties(): void {
    this.propertyService.getAvailableProperties().subscribe({
      next: (data) => {
        this.properties = data.filter(p => p.seller?.id === this.sellerId);
        this.errorMessage = '';
        this.selectedProperty = undefined;
        this.viewMode = 'list';
      },
      error: (err) =>
        (this.errorMessage = `Error loading available properties for seller: ${err.message || err}`)
    });
  }

  loadPropertyById(id: number) {
    if (this.selectedProperty && this.selectedProperty.id === id) {
      this.selectedProperty = undefined;
    } else {
      this.propertyService.getPropertyById(id).subscribe({
        next: (property) => {
          if (!property.manager) {
            property.manager = {
              id: 0,
              name: '',
              email: '',
              role: 'MANAGER',
              active: false
            } as User;
          }
          this.selectedProperty = property;
          this.scrollToSelected = true;
          this.viewMode = 'list';
        },
        error: (err) => {
          this.errorMessage = 'Error loading property details';
          console.error(err);
        }
      });
    }
    setTimeout(() => {
      const el = document.querySelector('.selected-property-form');
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    }, 100);
  }

  ngAfterViewChecked(): void {
    if (this.scrollToSelected && this.selectedPropertyCard) {
      this.selectedPropertyCard.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
      this.scrollToSelected = false;
    }
  }

  toggleAddForm() {
    if (this.viewMode === 'add') {
      this.viewMode = 'list';
    } else {
      this.viewMode = 'add';
      this.selectedProperty = undefined;
    }
  }

  setVal(event: any) {
    const val = event.target.value;
    if (val === 'Show Inquires') {
      this.viewMode = this.viewMode === 'inquiries' ? 'list' : 'inquiries';
      if (this.viewMode !== 'list') this.selectedProperty = undefined;
    } else if (val === 'Show Transactions') {
      this.viewMode = this.viewMode === 'transactions' ? 'list' : 'transactions';
      if (this.viewMode !== 'list') this.selectedProperty = undefined;
    }
  }

  addProperty(): void {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select an image file.';
      return;
    }

    const formData = new FormData();
    formData.append('property', JSON.stringify(this.newProperty));
    formData.append('title', this.newProperty.title);
    formData.append('description', this.newProperty.description);
    formData.append('price', this.newProperty.price.toString());
    formData.append('location', this.newProperty.location);
    formData.append('approved', this.newProperty.approved.toString());
    formData.append('seller.id', this.sellerId.toString()); // dynamically assigned sellerId
    formData.append('manager.id', this.newProperty.manager.id.toString());
    formData.append('type.id', this.newProperty.type.id.toString());
    formData.append('imageFile', this.selectedFile);

    this.propertyService.addProperty(formData).subscribe({
      next: (data) => {
        this.properties.push(data);
        this.errorMessage = '';
        alert('Property added successfully!');
        this.resetNewProperty();
      },
      error: (err) => this.errorMessage = `Error adding property: ${err.message || err}`
    });
  }

  updateProperty(): void {
    if (!this.selectedProperty) return;

    const formData = new FormData();
    formData.append('title', this.selectedProperty.title);
    formData.append('description', this.selectedProperty.description);
    formData.append('price', this.selectedProperty.price.toString());
    formData.append('location', this.selectedProperty.location);
    formData.append('approved', this.selectedProperty.approved.toString());
    formData.append('sellerId', this.selectedProperty.seller.id.toString());
    formData.append('managerId', this.selectedProperty.manager.id.toString());
    formData.append('typeId', this.selectedProperty.type.id.toString());

    if (this.selectedFile) {
      formData.append('imageFile', this.selectedFile);
    }

    this.propertyService.updateProperty(this.selectedProperty.id, formData).subscribe({
      next: (data) => {
        const index = this.properties.findIndex(p => p.id === data.id);
        if (index !== -1) this.properties[index] = data;
        alert('Property updated successfully!');
        this.selectedProperty = undefined;
        this.selectedFile = undefined;
      },
      error: (err) => {
        console.error('Update error:', err);
        this.errorMessage = `Update failed: ${err.message || err}`;
      }
    });
  }

  resetNewProperty() {
    this.newProperty = {
      id: 0,
      title: '',
      description: '',
      price: 0,
      location: '',
      imageUrl: '',
      approved: false,
      seller: { id: this.sellerId, name: '', email: '', role: 'SELLER', active: false },
      manager: { id: 0, name: '', email: '', role: 'MANAGER', active: false },
      type: { id: 0, name: '' },
      createdAt: new Date().toISOString()
    };
    this.selectedFile = undefined;
    this.viewMode = 'list';
  }

  deleteProperty(id: number, sellerId: number): void {
    // Use dynamic sellerId here, ignore passed sellerId param
    this.propertyService.deleteProperty(id, this.sellerId).subscribe({
      next: () => {
        this.properties = this.properties.filter((p) => p.id !== id);
        this.errorMessage = '';
        alert('Property deleted successfully!');
      },
      error: (err) =>
        (this.errorMessage = `Error deleting property: ${err.message || err}`)
    });
  }

  logout() {
    // Call your auth service to clear session/token
    this.authService.logout();

    // Navigate to login or home page after logout
    this.router.navigate(['/login']);
  }
}
