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
interface User {

  userRole : string;

  workclass: number;


}


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})


export class DashboardComponent implements OnInit {
  dataSource: MatTableDataSource<Product> = new MatTableDataSource<Product>(); // Initialize dataSource
  displayedColumns: string[] = ['product', 'price', 'quantity', 'value', 'actions'];
  isAdmin = false;

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




  canUseAdminMode(role:string, worclass:number){

    if(worclass > 1 || role == "admin"){

      this.isAdmin = true;

    }

  }


  manageUsers(){

  }


  manageOthers(){

  }
  manageItems(){

  }
// Define methods for each action
edit(element: any): void {
  // Implement edit functionality, e.g., open a dialog
  console.log('Edit clicked for element:', element);
}

delete(element: any): void {
  // Implement delete functionality
  console.log('Delete clicked for element:', element);
}

report(element: any): void {
  // Implement report functionality, e.g., open a dialog
  console.log('Report clicked for element:', element);
}

assign(element: any): void {
  // Implement assign functionality, e.g., navigate to another component
  console.log('Assign clicked for element:', element);
}




  userDetails: any;

  ngOnInit() {
    // Check if the user is authenticated
    if (!this.authService.isAuthenticated()) {
      // If not authenticated, redirect to login
      this.router.navigate(['/login']);
    }

    this.userService.getUserDetails().subscribe(
      (user:User) => {


        const stringRole = user.userRole;
        const worclass = user.workclass;

        console.log('User details:', stringRole +worclass ); // Log the response data

       this.canUseAdminMode( stringRole, worclass   )
        // console.log('User details:', data); // Log the response data
        // this.userDetails = data;


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


