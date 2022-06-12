
# Foodie

Foodie represents a Kotlin mobile application for browsing various recipes working with local and remote data sources, such as [Spoonacular API](https://spoonacular.com/food-api) and ROOM database. 

This document will be updated as the project progresses.


## Tools & Technologies

- Kotlin, Android Studio, Git Bash
- Data Binding, Navigation Component, RecyclerView, Kotlin Coroutines

- libraries
    - [ShimmerRecyclerView library](https://github.com/omtodkar/ShimmerRecyclerView)
        - pretty shimmer effect on [placeholder_row_layout.xml](https://github.com/laurakciic/foodie/blob/master/Foodie_RMA/app/src/main/res/layout/placeholder_row_layout.xml) while data is loading 

    - [Retrofit library](https://square.github.io/retrofit/)
        - a typesafe HTTP client
        - provides communication between client & server
        - one simple interface, inside which we can create multiple functions
        - each of the functions can represent a different HTTP request

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


