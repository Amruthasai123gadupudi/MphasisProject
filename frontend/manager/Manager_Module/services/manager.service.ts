import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
//import { Property } from '../models/property.model';
import { Observable } from 'rxjs';
import { Property } from '../model/property';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {
  private baseUrl = 'http://localhost:8082/api/manager';

  constructor(private http: HttpClient) {}

  getApproved(): Observable<Property[]> {
    return this.http.get<Property[]>(`${this.baseUrl}/approved`);
  }

  getPending(): Observable<Property[]> {
    return this.http.get<Property[]>(`${this.baseUrl}/pending`);
  }

  approveProperty(id: number): Observable<Property> {
    return this.http.put<Property>(`${this.baseUrl}/approve/${id}`, {});
  }

  editProperty(id: number, updated: Property): Observable<Property> {
    return this.http.put<Property>(`${this.baseUrl}/edit/${id}`, updated);
  }

  deleteProperty(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }
  getCombined(): Observable<{ approved: Property[]; pending: Property[] }> {
    return this.http.get<{ approved: Property[]; pending: Property[] }>(`${this.baseUrl}/combined`);
  }
}
