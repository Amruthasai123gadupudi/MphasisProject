import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Rule } from '../model/rule';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RulesService {
  private apiUrl = 'http://localhost:8082/api/admin/config/rules';
 
  constructor(private http: HttpClient) {}
 
  getRules(): Observable<Rule[]> {
    return this.http.get<Rule[]>(this.apiUrl);
  }
  getRulesRole(role: string) {
    return this.http.get<Rule[]>(`${this.apiUrl}/${role}`);
  }
 
  addRule(rule: Rule): Observable<Rule> {
    return this.http.post<Rule>(this.apiUrl, rule);
  }
 
  deleteRule(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
