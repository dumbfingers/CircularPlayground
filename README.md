## Circular Playground

This is a proof-of-concept app that utilising the latest tech stack:
* Jetpack Compose for UI
* Hilt for Dependency Injection
* Retrofit for Network 
* DataStore for Preferences Save/Load
* Kotlin Coroutines to replacing RxJava

The app is following Redux architecture in which UI is listening to the changes of `ViewState`, and `ViewState` is controlled by `ViewModel`.