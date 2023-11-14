import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-creation',
  templateUrl: './user-creation.component.html',
  styleUrls: ['./user-creation.component.css']
})
export class UserCreationComponent {
  user: any = { username: '', password: '', role: '' }; // Initialize user object

  constructor(private userService: UserService) { }

  onSubmit() {
    this.userService.createUser(this.user).subscribe(
      (response) => {
        console.log('User created successfully:', response);
        // Add further handling or navigation logic
      },
      (error) => {
        console.error('Error creating user:', error);
      }
    );
  }
}
