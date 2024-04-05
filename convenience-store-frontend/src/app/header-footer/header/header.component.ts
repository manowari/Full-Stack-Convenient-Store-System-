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



  logout() {
    this.authService.logout();
    // Optionally navigate to the login page after logout
    this.router.navigate(['/login']);
  }
}
