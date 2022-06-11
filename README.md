
# Foodie

Foodie represents a Kotlin mobile application for browsing various recipes working with local and remote - [Spoonacular API](https://spoonacular.com/food-api) data sources.

This document will be updated as the project progresses.     return

  
## Tools & Technologies

- Kotlin, Android Studio, Git Bash
- Data Binding, Navigation Component, RecyclerView, ShimmerRecyclerView library    return

## Obstacles & Solutions 

### 1. App crashes - NavHostFragment couldn't be found (with clean debbug, no errors)

Android Studio creates NavHostFragment in xml as a FragmentContainerView, not just as fragment - that's why it couldn't be found via findNavController() method.

According to the official [Android docs](https://developer.android.com/guide/navigation/navigation-getting-started) solution is:

my code:
```kotlin
navController = findNavController(R.id.navHostFragment)
```

SOLUTION:
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


