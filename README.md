## PTV Shows - Android App for Popular TV Series Information

PTV Shows is an Android app that lets you browse and discover popular TV series information from the [Movie Database](https://www.themoviedb.org/). Built with Jetpack Compose and Kotlin using the Clean Architecture design pattern, it offers a smooth and efficient experience for TV series enthusiasts.

## Features Overview

- **Browse:** Explore curated lists of trending series and uncover hidden gems.
- **Search:** Find your favorites in a keyword search.
- **Details:** Explore titles, overviews, release dates, ratings, taglines, seasons, types, status, casts, genres, episode counts, networks, production companies, and captivating poster/backdrop images.
- **Offline Support:** Enjoy the app anytime, anywhere – even without an internet connection.

## Building Blocks of the Architecture

App developed with Clean Architecture approach, separating concerns for modularity, maintainability, and testability.

- **Data:** Handles all data operations, fetching from the network and storing locally using Room.
- **Repositories:** Provide a consistent abstraction layer for accessing and manipulating data.
- **Domain:** Houses the core business logic, defining the app's use cases and rules.
- **Use Cases:** Implement specific functionalities the app performs, defining how data is obtained and processed.
- **Presentation:** Contains the Jetpack Compose UI components, ViewModels, and UI-related logic.
- **Dependency Injection (DI):** Leverages Dagger Hilt to easily manage object dependencies across layers, making code modular and testable.

## Technology Overview

PTV Shows app utilizes the following technologies to deliver a smooth and efficient experience:

- **Core Libraries**
    - **Jetpack Compose (Material3):** Modern UI framework with a beautiful Material Design aesthetic for an intuitive and visually appealing experience.
    - **Kotlin:** Expressive and concise programming language for clean, maintainable, and efficient code.
- **Architecture**
    - **Clean Architecture:** Separation of concerns for robust, testable, and scalable code base.
- **Networking**
    - **Retrofit:** Simplified REST API client with powerful builder options for streamlined data fetching.
    - **Okhttp3:** Efficient HTTP client for reliable and performant network communication.
- **Data Persistence**
    - **Room:** Local database solution for fast and secure storage and retrieval of app data.
- **Dependency Injection**
    **Dagger-Hilt:** Powerful dependency injection framework for reduced boilerplate, simplified object management, and improved testability.
- **Image Management**
    - **Coil:** Efficient and flexible image loading library for smooth display of posters and backdrops.
- **Observables & State Management**
    - **ViewModel:** Provides lifecycle-aware data for UI components, ensuring data consistency and efficient updates.
    - **Lifecycle:** Manages the lifecycle of components and observes changes for responsive behavior.
- **Navigation**
    - **Navigation:** Simplified management of navigation flow within the app for a seamless user experience.
- **Splash Screen & Animation**
    - **Splash screen:** Creates a polished and engaging app startup experience.
    - **Lottie Compose:** Integrates Lottie animations for captivating UI elements and interactions.

## Download

[<img src="./files/get_in_github.png" alt='Get it on GitHub' height="80">](https://github.com/manimaran96/ptv/releases/latest)

## Screens

### Splash & Home Screen

<p float="left">
    <img src="./files/splash.jpg" alt="Splash" width="230">
    <img src="./files/home.jpg" alt="Home" width="230">
</p>

### Details Screen

<p float="left">
    <img src="./files/details_1.jpg" alt="Details" width="230">
    <img src="./files/details_2.jpg" alt="Details" width="230">
</p>

### Seasons & Cast Details

<p float="left">
    <img src="./files/seasons.jpg" alt="Season" width="230">
    <img src="./files/cast.jpg" alt="Cast" width="230">
</p>

### Search Screen

<p float="left">
    <img src="./files/search.jpg" alt="Search" width="230">
    <img src="./files/search_results.jpg" alt="Search results" width="230">
</p>

### Loader, No results & Error Screen

<p float="left">
    <img src="./files/loader.jpg" alt="Loader" width="243">
    <img src="./files/no_results.jpg" alt="No results" width="230">
    <img src="./files/no_results_2.jpg" alt="Search results" width="230">
</p>

<p float="left">
    <img src="./files/error.jpg" alt="Error" width="230">
</p>


## Credits
- [App icon](https://www.flaticon.com/free-icon/tv-show_8164091?related_id=8164091)
- [Default image](https://www.flaticon.com/free-icon/picture_4675126?term=default+image&page=1&position=60&origin=search&related_id=4675126)
- [Loader lottie](https://lottiefiles.com/animations/trail-loading-LBtuXb8poE)
- [Error lottie](https://lottiefiles.com/animations/error-n2dxavyxSg)
- [Search lottie](https://lottiefiles.com/animations/searchask-loop-jEWhJnfkDE)
- [No results lottie](https://lottiefiles.com/animations/sad-empty-box-GaU6k5DfLa)

## Run

1. Clone this project and open into Android Studio.
2. Add your API key to `local.properties` like this
   `API_KEY = "507xxxxxxxxxxxxxxxxxxc5a4"`
3. Run the app

Get the API key [here](https://developer.themoviedb.org/docs/authentication-application)

