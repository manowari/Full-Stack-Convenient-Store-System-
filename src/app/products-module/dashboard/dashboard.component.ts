// dashboard.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

interface Product {
  product: string;
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
  dataSource: MatTableDataSource<Product>;
  displayedColumns: string[] = ['product', 'price', 'quantity', 'value'];

  totalValue: number = 0;
  outOfStockCount: number = 0;

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
    // Example data (replace with your actual data)
    const products: Product[] = [
      { product: 'Product A', price: 10, quantity: 50, value: 0 },
      { product: 'Product B', price: 20, quantity: 30, value: 0 },
      // Add more products as needed
    ];

    // Calculate total value and count of out-of-stock items
    this.totalValue = products.reduce((total, product) => total + product.price * product.quantity, 0);
    this.outOfStockCount = products.filter(product => product.quantity === 0).length;

    // Calculate and set the 'value' property for each product
    this.dataSource = new MatTableDataSource(products);
        // Set up sorting and pagination
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    // Check if the user is authenticated
    if (!this.authService.isAuthenticated()) {
      // If not authenticated, redirect to login
      this.router.navigate(['/login']);
    }


  }
}
