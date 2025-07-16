import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  private apiUrl = 'http://localhost:8082/api/analytics/top-sellers';

  constructor(private http: HttpClient) {}

  getTopSellers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
