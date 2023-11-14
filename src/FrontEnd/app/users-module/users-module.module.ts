import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCreationComponent } from './user-creation/user-creation.component';
import { UserManagementComponent } from './user-management/user-management.component';



@NgModule({
  declarations: [
    UserCreationComponent,
    UserManagementComponent
  ],
  imports: [
    CommonModule,
  ]
})
export class UsersModuleModule { }
