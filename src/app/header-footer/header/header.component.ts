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
    // Subscribe to router events to determine if the user is on the login page
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const logoutButton = document.getElementById('logoutButton');

        // Hide the button if the user is on the login page
        if (logoutButton) {
          logoutButton.style.display = event.url.endsWith('/login') ? 'none' : 'block';
        }
      }
    });
  }

  logout() {
    this.authService.logout();
    // Optionally navigate to the login page after logout
    this.router.navigate(['/login']);
  }
}
