import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PriceService {
  private baseUrl = 'http://localhost:8080/api/prices';

  constructor(private http: HttpClient) { }

  createPrice(price: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, price);
  }

  getAllPrices(): Observable<any> {
    return this.http.get(`${this.baseUrl}/all`);
  }

  getPriceById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  updatePrice(id: number, price: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/update/${id}`, price);
  }

  deletePrice(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`);
  }
}
