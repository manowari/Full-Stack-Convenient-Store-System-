import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { CreateProductComponent } from './create-product/create-product.component';
import { UpdateProductComponent } from './update-product/update-product.component';
import { PriceManagementComponent } from './price-management/price-management.component';
import { ProductManagementComponent } from './product-management/product-management.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { MatTableModule, MatTableDataSource, _MatTableDataSource } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { CurrencyPipe } from '@angular/common';


@NgModule({
  declarations: [
    ProductListComponent,
    ProductDetailsComponent,
    CreateProductComponent,
    UpdateProductComponent,
    PriceManagementComponent,
    ProductManagementComponent,
    DashboardComponent
  ],
  imports: [

    CommonModule,
    MatTableModule,
    MatSortModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
   ],
  exports: [MatTableModule, MatSortModule, MatIconModule, MatInputModule, MatPaginatorModule],
})
export class ProductsModuleModule {}
