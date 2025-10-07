# Polatta - Chocolate Drink Ordering App

Polatta is a modern Android application designed for a fictional chocolate drink shop, "Pondok Cokelat Hatta." It provides a seamless and intuitive user experience for browsing menus, placing orders, and tracking their status. The app is built entirely with Jetpack Compose and follows modern Android development principles, including a clean, ViewModel-driven architecture.

## Features

-   **User Authentication**: Secure sign-in and registration using Firebase Authentication with email/password and Google Sign-In options.
-   **Role-Based Access**: Differentiates between regular customers and administrators, with a dedicated dashboard for admin functionalities.
-   **Interactive Menu**: Browse a variety of drinks and toppings categorized for easy navigation.
-   **Search Functionality**: Quickly find specific menu items.
-   **Recommendations**: A dedicated section to highlight recommended products.
-   **Shopping Cart**: Add items to the cart, adjust quantities, and view a summary before checkout.
-   **Order Management**: Place orders and track their status in real-time ("Ongoing" or "History").
-   **Order History & Reordering**: View past orders and easily reorder with a single tap.
-   **Favorites**: Mark items as favorites for quick access.
-   **User Profile**: View and edit user profile information.
-   **Admin Dashboard**: A placeholder for future administrative features to manage menus, orders, and users.
-   **Real-time Chat**: Users can communicate with the admin/shop through a built-in chat feature.

## Technology Stack

This project is built with a modern technology stack, emphasizing best practices in Android development:

-   **UI**:
    -   **Jetpack Compose**: The entire UI is built declaratively with Jetpack Compose, ensuring a reactive and maintainable user interface.
    -   **Material 3**: Implements the latest Material Design guidelines for a clean and modern look and feel.
    -   **Compose Navigation**: Handles all in-app navigation between different screens.

-   **Architecture**:
    -   **MVVM (Model-View-ViewModel)**: A clean and scalable architecture that separates UI from business logic.
    -   **Repository Pattern**: Acts as a single source of truth for data, abstracting data sources from the ViewModels.

-   **Asynchronous Programming**:
    -   **Kotlin Coroutines & Flow**: Used extensively for managing asynchronous operations and handling data streams, ensuring a responsive UI.

-   **Backend & Data**:
    -   **Firebase Authentication**: Manages user sign-in and authentication.
    -   **Firebase Firestore**: Used as the backend to store user data, including roles. (Note: Menu data is currently sourced from a dummy data source but is structured to be easily migrated to Firestore).

-   **Dependency Injection**:
    -   While not using a dedicated library like Hilt or Koin in this version, the project is structured to easily incorporate one. ViewModels and Repositories are instantiated in a way that facilitates future DI integration.

-   **Gradle**:
    -   **Gradle Kotlin DSL**: Manages project dependencies and build configurations.
    -   **Version Catalog (libs.versions.toml)**: Centralizes dependency management for consistency and easier updates.

## Project Structure

The project is organized into several packages, following a clean and modular structure:

-   `data`: Contains data-related classes, including data sources and repositories.
    -   `datasource`: Defines the contract for data sources and provides a dummy implementation (`DummyMenuDataSource`).
    -   `repository`: The `MenuRepository` and `AuthRepository` classes serve as the single source of truth for menu and authentication data.
-   `model`: Contains the data classes (`MenuItem`, `Order`, `User`) that represent the app's data structures.
-   `ui`: Contains all Jetpack Compose UI components and screens.
    -   `components`: Reusable UI components like `BottomNavBar`, `SearchBar`, and `MenuCard`.
    -   `navigation`: Defines the navigation graph and screens.
    -   `screens`: Composable functions for each screen of the app (e.g., `PolattaScreen`, `LoginScreen`, `ProfileScreen`).
    -   `theme`: Defines the app's color scheme, typography, and overall theme.
-   `viewmodel`: Contains the `MenuViewModel` and `AuthViewModel`, which hold and manage UI-related data and business logic.

## Getting Started

To get a local copy up and running, follow these simple steps:

### Prerequisites

-   Android Studio Iguana | 2023.2.1 or later.
-   A Google account for Firebase setup.

### Installation

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/nazmihakim/polattaapp.git](https://github.com/nazmihakim/polattaapp.git)
    ```
2.  **Open in Android Studio:**
    -   Open Android Studio and select "Open an existing project."
    -   Navigate to the cloned repository and open it.
3.  **Firebase Setup:**
    -   This project is configured to use Firebase. To connect it to your own Firebase project, you will need to:
        1.  Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
        2.  Add an Android app to your Firebase project with the package name `com.example.pondokcokelathatta`.
        3.  Download the `google-services.json` file and place it in the `app/` directory of the project.
        4.  In the Firebase Console, enable **Authentication** with the "Email/Password" and "Google" sign-in methods.
        5.  Enable **Cloud Firestore** and create a "users" collection to store user roles.
4.  **Build and Run:**
    -   Let Android Studio sync the Gradle files.
    -   Run the app on an emulator or a physical device.
