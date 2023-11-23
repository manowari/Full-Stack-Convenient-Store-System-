// auth/login/login.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private authService: AuthService
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  ngOnInit() {}

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const username = this.loginForm.value.username;
      const password = this.loginForm.value.password;

      // Hardcoded user credentials (replace with actual authentication logic)
      const hardcodedUsers = [
        { username: 'user1', password: 'password1' },
        { username: 'user2', password: 'password2' },
 
      ];

      const user = hardcodedUsers.find((u) => u.username === username && u.password === password);

      if (user) {
        // Simulate successful login
        this.authService.login(user.username, user.password);

        // Display a snackbar notification for successful login
        this.snackBar.open('Login successful', 'Close', { duration: 3000 });

        // Log navigation attempt
        console.log('Navigating to dashboard...');

        // Redirect to the dashboard
        this.router.navigate(['/dashboard']).then(() => {
          // Log after navigation
          console.log('Navigation to dashboard successful');
        }).catch((error) => {
          // Log navigation error
          console.error('Error navigating to dashboard:', error);
        });
      } else {
        // Display a snackbar notification for unsuccessful login
        this.snackBar.open('Invalid credentials', 'Close', { duration: 3000 });
        console.log('Invalid credentials');
      }
    }
  }


  ///jwt login


//   onSubmit() {
//     if (this.loginForm.valid) {
//       const username = this.loginForm.value.username;
//       const password = this.loginForm.value.password;

//       this.authService.login(username, password).subscribe(
//         (success) => {
//           if (success) {
//             // Display a snackbar notification for successful login
//             this.snackBar.open('Login successful', 'Close', { duration: 3000 });

//             // Log navigation attempt
//             console.log('Navigating to dashboard...');

//             // Redirect to the dashboard
//             this.router.navigate(['/dashboard']).then(() => {
//               // Log after navigation
//               console.log('Navigation to dashboard successful');
//             }).catch((error) => {
//               // Log navigation error
//               console.error('Error navigating to dashboard:', error);
//             });
//           } else {
//             // Display a snackbar notification for unsuccessful login
//             this.snackBar.open('Invalid credentials', 'Close', { duration: 3000 });
//             console.log('Invalid credentials');
//           }
//         },
//         (error) => {
//           // Handle error, if any
//           console.error('Login error:', error);
//         }
//       );
//     }


// }

}
