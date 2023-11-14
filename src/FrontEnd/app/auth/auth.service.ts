// auth/auth.service.ts
import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private timeoutId: any;

  timeout = 2*60*1000;

  login(username: string, password: string): void {
    // In a real application, you'd typically send a request to your authentication service
    // to get the token. For simplicity, we're using a hardcoded token here.
    const token = 'fake-jwt-token';
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, username);
    this.resetAutoLogout();

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
}
