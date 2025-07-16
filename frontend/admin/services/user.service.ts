import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role, User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private base = 'http://localhost:8082/api/admin/users';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.base);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.base}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.base, user);
  }

  // updateUserStatus(id: number, active: boolean): Observable<void> {
  //   return this.http.put<void>(`${this.base}/${id}/status`, null, { params: new HttpParams().set('active', active.toString()) });
  updateUserStatus(id: number, active: boolean): Observable<any> {
    const params = new HttpParams().set('active', active.toString());
    return this.http.put(`${this.base}/${id}/status`, null, { params });
  }
  
  updateUserRole(id: number, role: Role): Observable<void> {
    return this.http.put<void>(`${this.base}/${id}/role`, null, { params: new HttpParams().set('role', role) });
  }

  getUsersByRole(role: Role): Observable<User[]> {
    return this.http.get<User[]>(`${this.base}/role/${role}`);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
