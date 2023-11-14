# Full Stack Convenience Store System - Angular Frontend


<img src="https://github.com/manowari/Full-Stack-Convenient-Store-System-/assets/141199798/e6b70f9f-f3e1-4533-8a93-0d764f04c357" alt="Image" width="400" height="300">



This repository folder contains the Angular frontend for the Full Stack Convenience Store System.

## Project Overview

The Full Stack Convenience Store System is a comprehensive system designed to manage inventory, user authentication, and product information for a convenience store. This repository specifically focuses on the Angular frontend.

## Folder Structure

The project follows a modularized structure for better organization. Here is an overview of the key folders and their contents:

- **app**
  - **auth**: Contains components and modules related to user authentication.
  - **header-footer**: Contains components for the header and footer of the application.
  - **products-module**: Modules and components related to product management.
  - **users-module**: Modules and components related to user management.
  - **app.component.ts**: The main component that serves as the entry point for the application.
  - **app.module.ts**: The main module that orchestrates the application.

- **assets**: Placeholder folder to store static assets.

## Dependencies

The project utilizes the following key dependencies:

- **Angular**: Version 16.2.5
- **Angular Material**: Version 16.2.7
- **Bootstrap**: Version 5.1.3
- **Ngx Toastr**: Version x.x.x (for toast notifications)

For a complete list of dependencies, refer to the `package.json` file.

## Key Components

### 1. Login Form Component

The login form component (`auth/login-form`) is responsible for handling user authentication. It utilizes Angular Material components for input fields and buttons and incorporates Bootstrap for styling. The component includes features such as:

- Reactive form for user input.
- Integration with Angular Material Snackbar for toast notifications.
- Option to view/hide the password using a toggle button.

### 2. Dashboard Component

The dashboard component (`dashboard`) serves as a protected page accessible only to authenticated users. It provides a basic welcome message and information about the system.

## Usage

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/store-inventory-frontend.git
