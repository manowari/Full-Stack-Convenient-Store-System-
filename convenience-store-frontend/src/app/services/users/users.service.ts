import { AuthService } from 'src/app/auth/auth.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/app/config';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  headers:any;
    private baseURL = `${environment.apiUrl}/users`;

   constructor(private http: HttpClient, private authApi: AuthService) {

   }

  getUserDetails(): Observable<any> {

    const username = this.authApi.getUsername()
   const headers  = this.authApi.getHeaders()

    return this.http.get<any>(`${this.baseURL}/${username}`, {  headers })
      .pipe(
        catchError((error) => {
          throw 'Error getting user details: ' + error;
        })
      );
  }

  createUser(user: any): Observable<any> {
    return this.http.post(`${this.baseURL}/create`, user);
  }

  getAllUsers(): Observable<any> {
    return this.http.get(`${this.baseURL}/all`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.baseURL}/${id}`);
  }

  updateUser(id: number, user: any): Observable<any> {
    return this.http.put(`${this.baseURL}/update/${id}`, user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseURL}/delete/${id}`);
  }




}
