import { AuthService } from 'src/app/auth/auth.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { environment } from '../config';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient, private authApi: AuthService ) { }

  createProduct(product: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, product);
  }




  getAllProducts(): Observable<any> {

    const username = this.authApi.getUsername()
   const headers  = this.authApi.getHeaders()

    return this.http.get<any>(`${this.baseUrl}/all`, {  headers })
      .pipe(
        catchError((error) => {
          throw 'Error getting user details: ' + error;
        })
      );
  }


  // getAllProducts(): Observable<any> {
  //   return this.http.get(`${this.baseUrl}/all`);
  // }

  getProductById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  updateProduct(id: number, product: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/update/${id}`, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`);
  }
}
