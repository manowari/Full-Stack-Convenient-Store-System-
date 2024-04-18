import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  constructor(private authService: AuthService, private router: Router) {


  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }


 /* The `logout()` method in the HeaderComponent class is responsible for handling the logout
 functionality when a user initiates the logout process. Here's a breakdown of what the method does: */
  logout() {
    this.authService.logout().subscribe(
      response => {
        console.error('Logout failed:', response);

        // Handle successful logout
        localStorage.removeItem('token'); // Remove token from local storage
        this.router.navigate(['/login']); // Redirect to login page or any other desired page
      },
      error => {
        if (error instanceof ErrorEvent) {
          // Client-side error occurred, handle appropriately
          console.error('Client-side error occurred:', error.message);
        } else {
          // Server-side error occurred
          console.error('Server-side error occurred:', error);

          // Handle logout failure
          // Display a simple alert dialog to the user
          alert('Logout failed. Please try again later.');
        }

        // In any case, redirect the user to the login page or any other desired page
        this.router.navigate(['/login']);
      }
    );
  }
  }
