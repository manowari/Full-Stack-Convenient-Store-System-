import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../config';

const TOKEN_KEY = 'auth-token';
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


  constructor(private http: HttpClient) {}
  private TOKEN_KEY = 'auth_token';

  login(credentials: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json' // Adjust content type if needed
    });
  
    const API_URL = `${this.baseURL}/login`;
    return this.http.post<any>(API_URL, credentials, { headers }).pipe(
      tap((response) => {
        console.log('Received response:', response, "crer", credentials); // Log the entire response
        const responseBody = response.body;
        if (responseBody && responseBody.token) {
          const token = responseBody.token;
          console.log('Received token:', token); // Log the received token
          localStorage.setItem('TOKEN_KEY', token);
        }
      }),
      catchError(this.manageError)
    );
  }



  losgin(credentials: any): Observable<any> {
    const API_URL = `${this.baseURL}/auth/login`; // Assuming the login endpoint is '/auth/login'
    return this.http.post<any>(API_URL, credentials).pipe(
      tap((response) => {
        console.log('Received response:', response); // Log the entire response
        if (response && response.token) {
          const token = response.token;
          console.log('Received token:', token); // Log the received token
          localStorage.setItem(this.TOKEN_KEY, token);
        } else {
          // Handle the case where no token is received
          console.error('No token received in the response');
          throw new Error('Authentication failed'); // Emit an error to be caught by catchError
        }
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(error); // Rethrow the error for the component to handle
  }
  // login(credentials: any): Observable<any> {
  //   const API_URL = `${this.baseURL}/login`;
  //   return this.http.post<any>(API_URL, credentials, { observe: 'response' }).pipe(
  //     tap((response) => {
  //       console.log('Received response:', response); // Log the entire response
  //       const responseBody = response.body;
  //       if (response.status === 200 && responseBody && responseBody.token) {
  //         const token = responseBody.token;
  //         console.log('Received token:', token); // Log the received token
  //         localStorage.setItem(TOKEN_KEY, token);
  //       }
  //     }),
  //     catchError(this.manageError)
  //   );
  // }
  
  

  // login(credentials: any): Observable<any> {
  //   let API_URL = `${this.baseURL}/login`;
  //   return this.http.post(API_URL, credentials, { withCredentials: false }).pipe(
  //     tap((res: any) => {

  //       console.log(' In API Received token:', JSON.stringify(res.body), "cred :" , credentials);
  //       // Extract the token from the response body
  //       const token = res.token;
  //       // Store the token securely (e.g., in localStorage)
  //       localStorage.setItem(TOKEN_KEY, token);
  //       // Optionally, decode and validate the token
  //       const isTokenValid = !this.jwtHelper.isTokenExpired(token);
  //       if (!isTokenValid) {
  //         // Handle invalid token
  //       }
  //     }),
  //     catchError(this.manageError)
  //   );
  // }

  manageError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Sorry! ${error.error.message}`;
    }
    return throwError(errorMessage);
  }

  loginViaApi(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }, { withCredentials: true }).pipe(
      tap((response) => {
        // No need to handle token in the response since it will be stored in HttpOnly cookie
      }),
      catchError((error) => {
        return throwError(error);
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/logout`, {}, { withCredentials: true }).pipe(
      tap(() => {
        if (this.timeoutId) {
          clearTimeout(this.timeoutId);
        }
      }),
      catchError((error) => {
        return throwError(error);
      })
    );
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
  
  
  getUsername(): string | null {
    return localStorage.getItem(USER_KEY);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token && !this.jwtHelper.isTokenExpired(token);
  }
  
}
