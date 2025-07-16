import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Save logged-in user info to localStorage
  setCurrentUser(user: any): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }
 
  // Retrieve logged-in user's ID
  getCurrentUserId(): number | null {
    const userJson = localStorage.getItem('currentUser');
    if (!userJson) return null;
    try {
      const user = JSON.parse(userJson);
      return user?.id ?? null;
    } catch {
      return null;
    }
  }
  //--------------------------------------------------
  getCurrentUserRole(): string | null {
    const userJson = localStorage.getItem('currentUser');
    if (!userJson) {
      return null;
    }
    try {
      const user = JSON.parse(userJson);
      return user?.role ?? null;
    } catch {
      return null;
    }
  } 
  //--------------------------------------------------
 
  getCurrentUser(): any | null {
    const userJson = localStorage.getItem('currentUser');
    if (!userJson) return null;
    try {
      return JSON.parse(userJson);
    } catch {
      return null;
    }
  }
 
  private baseUrl = 'http://localhost:8082/api/auth';
 
  constructor(private http: HttpClient) {}
 
  login(email: string, password: string): Observable<any> {
    const params = new HttpParams()
      .set('email', email)
      .set('password', password);
 
    return this.http.post(`${this.baseUrl}/login`, null, { params });
  }
 
  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }
 
  storeToken(token: string): void {
    localStorage.setItem('authToken', token);
  }
 
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }
 
 
 
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
 
  getUserRole(): string | null {
    const token = this.getToken();
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.role || null;
    }
    return null;
  }
 
 
  logout(): void {
    localStorage.removeItem('currentUser');
    // TODO: Add routing or other logout steps if needed
  }
}
function jwtDecode(token: string): any {
  throw new Error('Function not implemented.');
}

