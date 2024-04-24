import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { environment } from '../config';

const TOKEN_KEY = 'token';
const USER_KEY = 'auth-user';

interface LoginResponse {
  token: string;
}


@Injectable({
  providedIn: 'root',
})
export class AuthService {
   private baseURL = `${environment.authUrl}`;
  private timeoutId: any;
  private apiUrl = '/api';
  private jwtHelper: JwtHelperService = new JwtHelperService();
  isLoggedIn = false; // Define isLoggedIn property



  username = ""
  headers = new HttpHeaders().set('Content-Type', 'application/json');






  constructor(private http: HttpClient) {}
  private TOKEN_KEY = 'auth_token';



signin(data:any){
  let API_URL = `${this.baseURL}/login`;
  return this.http.post(API_URL, data, { headers: this.headers, withCredentials: false }).pipe(map(res => {

    console.log('Received response:', res ); // Log the entire response
    this.isLoggedIn = true;
    this.username =  data.email;

        return res || {}


      }),
        catchError(this.manageError)
      )
}


logout(){

  const headers = this.getHeaders(); // Get headers with authentication token

  let API_URL = `${this.baseURL}/logout`;
  console.log('Headers:', headers); // Log the headers


  return this.http.post(API_URL, { headers: headers, }).pipe(map(res => {

    console.log('Received response:', res ); // Log the entire response

    this.isLoggedIn = false;

        return res || {}


      }),
        catchError(this.manageError)
      )
}


// logout(): Observable<any> {
//   this.isLoggedIn = false;

//   const headers = this.getHeaders(); // Get headers with authentication token
//   return this.http.post<any>(`${this.apiUrl}/logout`, {}, { headers }).pipe(

//     tap(() => {
//       if (this.timeoutId) {


//         clearTimeout(this.timeoutId);
//       }
//     }),
//     catchError((error) => {
//       return throwError(error);
//     })
//   );
// }


removeToken(): void {
  localStorage.removeItem(this.TOKEN_KEY);
}


getHeaders(): HttpHeaders {
  const token = this.getToken();

  // Log the token
  console.log('Token:', token);

  // Create a new HttpHeaders object
  let headers = new HttpHeaders();

  // Set the Content-Type header
  headers = headers.set('Content-Type', 'application/json');

  // Set the Authorization header if the token exists
  if (token) {
    headers = headers.set('Authorization', `Bearer ${token}`);
  }

  console.log('Headers:', headers); // Log the headers

  return headers;
}




  manageError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Sorry! ${error.error.message}`;
    }
    return throwError(errorMessage);
  }




  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }



  resetAutoLogout() {
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
    const token = this.getToken();
    if (token) {
      const expirationDate = this.jwtHelper.getTokenExpirationDate(token);
      if (expirationDate) {
        const timeout = expirationDate.getTime() - Date.now();
        if (timeout > 0) {
          this.timeoutId = setTimeout(() => {
            this.logout();
          }, timeout);
        } else {
          // Token is expired
          this.logout();
        }
      } else {
        // Invalid token
        this.logout();
      }
    } else {
      // Token not present
      this.isLoggedIn = false; // Set isLoggedIn to false if token is not present
    }
  }


  getUsername()  {
    return this.username;
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token && !this.jwtHelper.isTokenExpired(token);
  }

}
