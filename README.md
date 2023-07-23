# Swipe Products

## Introduction
This is a multi module Android app made for Swipe Interview screening, It consists of two screens - a product listing screen and an add product screen. The app allows users to view a list of products, search for products, and add new products to the list. It follows the MVVM architecture, utilizes Retrofit for REST API calls, KOIN for Dependency Injection, and Lifecycle for Kotlin coroutines. Additionally, the app implements Navigation Component from Jetpack to navigate between fragments, and has also been segregated into two modules:
- core: for all its core functionalities such as network service etc.
- app: for UI and app layer tasks.

It improves the code readability as well as increases the hot build time significantly.

## Screenshots

<div style="display: flex; flex-wrap: wrap;">
  <img src="https://firebasestorage.googleapis.com/v0/b/storagebucket-a235c.appspot.com/o/Swipe%2FScreenshot_20230723_033557.png?alt=media&token=776bb58d-9411-45d7-b353-f90071534855" alt="Product Listing Screen" width="30%" />
  <img src="https://firebasestorage.googleapis.com/v0/b/storagebucket-a235c.appspot.com/o/Swipe%2FScreenshot_20230723_033639.png?alt=media&token=0931e8be-2fe9-44bc-a848-6ee1ee2f0a37" alt="Product Search" width="30%" />
  <img src="https://firebasestorage.googleapis.com/v0/b/storagebucket-a235c.appspot.com/o/Swipe%2FScreenshot_20230723_033658.png?alt=media&token=3079ff61-ae4b-4c7d-b2d9-066f3bd824fa" alt="Add Product Screen" width="30%" />
</div>

## Video Demo
[Swipe Products Demo](https://www.youtube.com/shorts/Z_8PxZwRzTw)

## Technologies Used

The app leverages modern technologies and follows best practices to ensure efficiency and maintainability. The major tech used in this project are:

- MVVM Architecture: Separating the app into layers of Model, View, and ViewModel to promote a clean and maintainable codebase.
- Retrofit: Handling REST API calls to retrieve and send data to the server.
- KOIN: Managing dependency injection to decouple classes and improve testability.
- Kotlin Coroutines with Lifecycle: Managing background tasks and asynchronous operations efficiently while respecting Android lifecycle.
- Navigation Component: Navigating between fragments in the app seamlessly.
- Sealed Classes: Handling generic responses and providing clear and consistent feedback to users.

## Testing

The app has been thoroughly tested to ensure its functionality and stability in various scenarios and conditions.


## APK Download

You can download the APK of the app using the following link:

[APK Download Link](https://firebasestorage.googleapis.com/v0/b/storagebucket-a235c.appspot.com/o/Swipe%2Fapk%2Fsuraj-giri-swipe.apk?alt=media&token=12de888d-56da-4344-ac59-b9b8c24bb06f)

Please make sure to enable installation from unknown sources in your device settings to install the APK.

## Conclusion

This Android app successfully fulfills the given assignment requirements. It demonstrates the use of modern technologies and best practices, providing users with a smooth and user-friendly experience for viewing and adding products. If you have any questions or need further assistance, feel free to contact me.

Thank you for reviewing my work!
