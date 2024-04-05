import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../config';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  private baseURL = `${environment.authUrl}`;
  private timeoutId: any;
  private apiUrl = '/api';
  private jwtHelper: JwtHelperService = new JwtHelperService();
  isLoggedIn = false; // Define isLoggedIn property

  constructor(private http: HttpClient) {}
  login(credentials: any): Observable<any> {
    let API_URL = `${this.baseURL}/login`;
    return this.http.post(API_URL, credentials, { headers: this.headers, withCredentials: false }).pipe(
      tap((res: any) => {
        // Assuming the token is returned in the 'token' property of the response
        const token = res.token;
        // Store the token securely (e.g., in localStorage)
        localStorage.setItem(TOKEN_KEY, token);
        // Optionally, decode and validate the token
        const isTokenValid = !this.jwtHelper.isTokenExpired(token);
        if (!isTokenValid) {
          // Handle invalid token
        }
      }),
      catchError(this.manageError)
    );
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
