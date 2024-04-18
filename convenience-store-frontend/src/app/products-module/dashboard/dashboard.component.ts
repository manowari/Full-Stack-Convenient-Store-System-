import { UserService } from 'src/app/services/users/users.service';
// dashboard.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { ProductService } from '../product.service';

interface Product {
  name: string;
  price: number;
  quantity: number;
  value: number;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})


export class DashboardComponent implements OnInit {
  dataSource: MatTableDataSource<Product> = new MatTableDataSource<Product>(); // Initialize dataSource
  displayedColumns: string[] = ['product', 'price', 'quantity', 'value'];

  totalValue: number = 0;
  outOfStockCount: number = 0;

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private productService: ProductService,
    private userService: UserService,

    private authService: AuthService,
    private router: Router,
  ) {
    // Example data (replace with your actual data)

  }





  userDetails: any;

  ngOnInit() {
    // Check if the user is authenticated
    if (!this.authService.isAuthenticated()) {
      // If not authenticated, redirect to login
      this.router.navigate(['/login']);
    }

    this.userService.getUserDetails().subscribe(
      (data) => {
        console.log('User details:', data); // Log the response data
        this.userDetails = data;
      },
      (error) => {
        console.error(error);
      }
    );

    this.productService.getAllProducts().subscribe(
      (products: Product[]) => {
        console.log('User details:', products); // Log the response data


        this.dataSource.data = products;
        this.calculateSummary(products);
      },
      error => console.error('Error fetching products:', error)
    );



  }



  calculateValue(product: Product): number {
    return product.price * product.quantity;
  }

  calculateSummary(products: Product[]): void {
    this.totalValue = products.reduce((acc, cur) => acc + (cur.price * cur.quantity), 0);
    this.outOfStockCount = products.filter(product => product.quantity === 0).length;
  }

}


