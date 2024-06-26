// auth/login/login.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';



const TOKEN_KEY = 'auth-token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private authService: AuthService
  ) {
    
  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }



  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }
  
    const credentials = this.loginForm.value;
  
    this.authService.signin(credentials).subscribe(
      (response: any) => { // Add type assertion here
        if (response && response.token) { // Access token property
          const token = response.token;
          // Store the token in localStorage or wherever you are storing it
          localStorage.setItem('token', token);
  
          // Optionally, update isLoggedIn status
          this.authService.isLoggedIn = true;
  
          this.snackBar.open('Login successful', 'Close', { duration: 3000 });
          this.router.navigate(['/dashboard']);
        } else {
          // Handle case where token is not found in the response
          this.snackBar.open('Token not found in the response', 'Close', { duration: 3000 });
        }
      },
      (error) => {
        if (error.status === 401) {
          // Handle case where credentials are incorrect
          this.snackBar.open('Invalid credentials. Please check your email and password and try again.', 'Close', { duration: 3000 });
        } else {
          // Handle other errors
          this.snackBar.open('An error occurred while logging in. Please try again later.', 'Close', { duration: 3000 });
        }
      }
    );
  }
  


  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
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

