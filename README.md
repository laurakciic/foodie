
# Foodie

Foodie represents a Kotlin mobile application for browsing various recipes working with local and remote data sources, such as [Spoonacular API](https://spoonacular.com/food-api) and ROOM database. 

This document will be updated as the project progresses.


## Tools & Technologies

- Kotlin, Android Studio, Git
- Navigation Component, RecyclerView, Kotlin Coroutines

- libraries
    - [ShimmerRecyclerView](https://github.com/omtodkar/ShimmerRecyclerView)
        - library which provides pretty shimmer effect on [placeholder_row_layout.xml](https://github.com/laurakciic/foodie/blob/master/Foodie_RMA/app/src/main/res/layout/placeholder_row_layout.xml) while data is loading 

    - [Retrofit](https://square.github.io/retrofit/)
        - a typesafe HTTP client library
        - provides communication between client & server
        - one simple interface, inside which we can create multiple functions
        - each of the functions can represent a different HTTP request

    - [Dagger](https://developer.android.com/training/dependency-injection/dagger-basics)
        - [dependancy injection](https://developer.android.com/training/dependency-injection) library for Java, Kotlin & Android
        - manages dependencies which results in limiting project's complexity as it scales up
        - automatically generates code that mimics the code you would otherwise have hand-written
        - provides fully static and compile-time dependencies addressing many of the dev & performance issues of reflection-based solutions (such as [Guice](https://en.wikipedia.org/wiki/Google_Guice))
        - because the code is generated at compile time, it's traceable and more performant than other reflection-based solutions such as Guice

    - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
        - dependancy injection library for Android
        - built on top of Dagger so it can reduce complexity even more
        - provides a standard way to incorporate Dagger dependency injection into Android app
        - defines a standard way to do DI by providing containers for every Android class in project and managing their lifecycles automatically
        - built on top of Dagger to benefit from compilte time correctness, runtime performance, scalability and Android Studio support 

    - [Data Binding]()
        - support library which makes possible to bind data to views in declarative format using XML layouts instead of setting them programatically
        - generates new class behind the scenes 
            - ex. for fragment_main.xml -> FragmentMainBinding class will be generated
            - new class can be used to bind actual data from UI with an actual variable, from activities/fragments

        - converts any XML layout file to Data Binding Layout
            - root element on that layout file becomes layout element 
            - that layout element will contain all other views inside
            - we can also create diff variables that can easily be used to set data directly on views
        - helps to deal with findViewById in more elegant way to reduce boiler plate code & has many other benefits
        - [Binding Adapters]()
            - used to create custom binding attribute for views inside layout XML file


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


