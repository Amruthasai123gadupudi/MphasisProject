import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PropertyType } from '../model/property-type';

@Injectable({
  providedIn: 'root'
})
export class PlatformConfigService {

  private baseUrl = 'http://localhost:8082/api/admin/config';
  apiUrl: any;
 
  constructor(private http: HttpClient) {}
 
  // Property Categories
  getCategories(): Observable<PropertyType[]> {
    return this.http.get<PropertyType[]>(`${this.baseUrl}/categories`);
  }
 
  addCategory(category: PropertyType): Observable<PropertyType> {
    return this.http.post<PropertyType>(`${this.baseUrl}/categories`, category);
  }
 
  updateCategory(id: number, category: PropertyType): Observable<PropertyType> {
    return this.http.put<PropertyType>(`${this.baseUrl}/categories/${id}`, category);
  }
 
  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/categories/${id}`);
  }
 
  // Notifications - FIXED: specify responseType to handle plain text response
  sendNotification(notification: Notification): Observable<string> {
    return this.http.post<string>(
      `${this.baseUrl}/notifications`,
      notification,
      { responseType: 'text' as 'json' }
    );
  }
 
// //added
// // platform-config.service.ts (add this method)
setRule(rule: string): Observable<string> {
  return this.http.post(`${this.baseUrl}/rules`, rule, { responseType: 'text' });
}
// added this methods for rules
// Add inside the service class
 
//... inside PlatformConfigService class
 
getRules(): Observable<string[]> {
  return this.http.get<string[]>(`${this.baseUrl}/rules`);
}
 
addRule(rule: string): Observable<string> {
  return this.http.post<string>(`${this.baseUrl}/rules`, rule, { responseType: 'text' as 'json' });
}
 
}
