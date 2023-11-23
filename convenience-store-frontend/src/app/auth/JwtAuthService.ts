// auth/jwt-auth.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JwtAuthService {
  // Replace this method with your actual JWT authentication logic
  authenticate(username: string, password: string): Observable<boolean> {
    // Simulate successful authentication for testing purposes
    if (username === 'user' && password === 'password') {
      return of(true);
    } else {
      return of(false);
    }
  }
}
