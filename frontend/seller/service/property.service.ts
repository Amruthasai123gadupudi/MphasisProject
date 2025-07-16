import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Property } from '../model/property';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private apiUrl = 'http://localhost:8082/api/properties';

  constructor(private http: HttpClient) { }

  getAllProperties(): Observable<Property[]> {
    return this.http.get<Property[]>(this.apiUrl);
  }

  getPropertiesBySeller(sellerId: number): Observable<Property[]> {
    return this.http.get<Property[]>(`${this.apiUrl}/seller/${sellerId}`);
  }

  getAvailableProperties(): Observable<Property[]> {
    return this.http.get<Property[]>(`${this.apiUrl}/available`);
  }

  getPropertyById(id: number): Observable<Property> {
    return this.http.get<Property>(`${this.apiUrl}/${id}`);
  }

  addProperty(formData: FormData): Observable<Property> {
    return this.http.post<Property>(this.apiUrl, formData);
  }

  updateProperty(id: number, formData: FormData): Observable<Property> {
    return this.http.put<Property>(`${this.apiUrl}/${id}`, formData);
  }

  deleteProperty(id: number, sellerId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}?sellerId=${sellerId}`);
  }
}
