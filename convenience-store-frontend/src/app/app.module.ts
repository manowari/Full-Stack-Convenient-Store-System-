import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';


import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderFooterModule } from './header-footer/header-footer.module';
import { LoginComponent } from './auth/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';

import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AuthRoutingModule } from './auth/auth-routing.module';


import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
 import { MatPaginatorModule } from '@angular/material/paginator';
import { CurrencyPipe } from '@angular/common';
import { MatTableDataSource } from '@angular/material/table';
import { ProductsModuleModule } from './products-module/products-module.module';
import { JwtInterceptor } from './auth-module/jwt.interceptor';
import { MatTooltipModule } from '@angular/material/tooltip';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
   ],
  imports: [
    MatTableModule,
    ProductsModuleModule,
    HttpClientModule,
    MatTooltipModule,
    MatSortModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
    AuthRoutingModule,
     MatInputModule,
    MatSnackBarModule,
    MatIconModule,
    MatFormFieldModule,
    MatButtonModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HeaderFooterModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatSortModule,
    MatIconModule,
    


   ],

   providers: [

    {

      provide: HTTP_INTERCEPTORS,
    useClass: JwtInterceptor,

    multi: true,},


  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
