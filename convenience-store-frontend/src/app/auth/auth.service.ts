import { JwtAuthService } from './JwtAuthService';
// auth/auth.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private timeoutId: any;

  timeout = 2 * 60 * 1000;

  // Add a configuration option for authentication method
  private useHardcodedLogin = true;

  constructor(private jwtAuthService: JwtAuthService) {} // Inject your JWT authentication service

  login(username: string, password: string): Observable<boolean> {
    if (this.useHardcodedLogin) {
      // Simulate successful login with hardcoded credentials
      const hardcodedUsers = [
        { username: 'user1', password: 'password1' },
        { username: 'user2', password: 'password2' },
      
      ];
      const user = hardcodedUsers.find((u) => u.username === username && u.password === password);

      if (user) {
        this.handleSuccessfulLogin(user.username);
        return of(true);
      } else {
        this.handleUnsuccessfulLogin();
        return of(false);
      }
    } else {
      // Use JWT authentication
      return this.jwtAuthService.authenticate(username, password);
    }
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  resetAutoLogout() {
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }

    // Set the timeout for 30 minutes (1800000 milliseconds)
    this.timeoutId = setTimeout(() => {
      this.logout();
    }, this.timeout);
  }

  getUsername(): string | null {
    return localStorage.getItem(USER_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private handleSuccessfulLogin(username: string): void {
    const token = 'fake-jwt-token'; // Replace with your actual JWT token
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, username);
    this.resetAutoLogout();
  }

  private handleUnsuccessfulLogin(): void {
    console.log('Invalid credentials');
    // You can handle this according to your application's requirements
  }
}
