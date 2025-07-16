import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
declare var bootstrap: any;

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  user:User=new User();
  favorites: any[] = [];
   // 🔁 This is static for now. You can replace it with dynamic login later.

  constructor(private http: HttpClient) {}
  ngOnInit(): void {
   
    this.getFavorites();
  }
 
  getFavorites(): void {
    const userJson = localStorage.getItem('user');
  
    if (userJson) {
      this.user = JSON.parse(userJson) as User;
      const buyerId = this.user.id;
  
      this.http.get<any[]>(`http://localhost:8082/api/buyer/favorites/${buyerId}`)
        .subscribe({
          next: (data) => this.favorites = data,
          error: (err) => console.error('Failed to load favorites', err)
        });
    } else {
      console.warn('User not found in localStorage');
      // Optionally redirect to login or show error
    }
  }
  

  
  
  
  removeFavorite(favId: number): void {
    this.http.delete(`http://localhost:8082/api/buyer/favorites/${favId}`)
      .subscribe(() => {
        // Filter it out from local array after successful deletion
        this.favorites = this.favorites.filter(f => f.id !== favId);
      });
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


}


