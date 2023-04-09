# SnakeComposeTestApp
Small app in Jetpack Compose and Android View

## Environment

- Android core KTX 1.9.1
- Gradle 7.5
- Jetpack Compose 1.4.1
- Compose Compiler version: 1.4.1
- Navigation Compose 2.5.3
- Material3 1.0.1
- Firebase BOM 31.0.3

The app is written in Compose, but the standard Android View was used for WebView. 
The application communicates with the Firebase backend (Remote Config and Realtime Database). 
The Android app is divided into two parts. A snake game and a WebView in which a link is opened and we can upload a photo.

Compose Navigation is used for navigation. For asynchronous tasks Kotlin Coroutines and Kotlin Flow.

Loading
![single_photo](https://user-images.githubusercontent.com/47458290/230794110-da144bd2-feec-458a-a951-63f9cd51d7ca.png)

Menu
![second](https://user-images.githubusercontent.com/47458290/230794171-606b9ada-f190-49ef-8496-ed22baafb896.png)

Game
![third](https://user-images.githubusercontent.com/47458290/230794205-9b511b93-28a5-4da7-ba2b-1678d9c145d0.png)
