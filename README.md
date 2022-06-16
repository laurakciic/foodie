
# Foodie

Foodie represents a Kotlin mobile application for browsing various recipes working with local and remote data sources, such as [Spoonacular API](https://spoonacular.com/food-api) and ROOM database. 

This document will be updated as the project progresses.


## Tools & Technologies

- Kotlin, Android Studio, Git Bash
- Data Binding, Navigation Component, RecyclerView, Kotlin Coroutines
- [Dependancy Injection](https://developer.android.com/training/dependency-injection)

- libraries
    - [ShimmerRecyclerView library](https://github.com/omtodkar/ShimmerRecyclerView)
        - pretty shimmer effect on [placeholder_row_layout.xml](https://github.com/laurakciic/foodie/blob/master/Foodie_RMA/app/src/main/res/layout/placeholder_row_layout.xml) while data is loading 

    - [Retrofit library](https://square.github.io/retrofit/)
        - a typesafe HTTP client
        - provides communication between client & server
        - one simple interface, inside which we can create multiple functions
        - each of the functions can represent a different HTTP request

    - [Dagger library](https://developer.android.com/training/dependency-injection/dagger-basics)
        - dependancy injection library for Java, Kotlin & Android
        - provides fully static and compile-time dependencies addressing many of the dev & performance issues of reflection-based solutions (such as [Guice](https://en.wikipedia.org/wiki/Google_Guice))

    - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
        - dependancy injection library for Android
        - defines a standard way to do DI by providing containers for every Android class in project and managing their lifecycles automatically
        - built on top of Dagger to benefit from compilte time correctness, runtime performance, scalability and Android Studio support 

- JSON to Kotlin plugin for generating Model classes for API 
    - the plugin lets us copy and paste JSON API response and it automatically generates necessary classes and fields (for API response) 

## Obstacles & Solutions 

### 1. NavHostFragment couldn't be found (app crashing with clean debbug, no errors)

Android Studio created NavHostFragment in xml as a FragmentContainerView, not just as fragment - that's why it couldn't be found via findNavController() method I used.

According to the official [Android docs](https://developer.android.com/guide/navigation/navigation-getting-started) solution is:

instead of (my code):
```kotlin
navController = findNavController(R.id.navHostFragment)
```

to use this:
```kotlin
val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
val navController = navHostFragment.navController
```

or the solution can also be to simply switch from 

```kotlin
<androidx.fragment.app.FragmentContainerView
...
```

to 

```kotlin
<fragment
...
```

in [activity_main.xml](https://github.com/laurakciic/foodie/blob/master/Foodie_RMA/app/src/main/res/layout/activity_main.xml), which I did.


