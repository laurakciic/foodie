
# Foodie

Foodie represents a Kotlin mobile application for browsing various recipes working with local and remote data sources, such as [Spoonacular API](https://spoonacular.com/food-api), SQLite & ROOM. 

This document will be updated as the project progresses.


## Tools & Technologies

- Kotlin, Android Studio, Git
- Navigation Component, RecyclerView, Kotlin Coroutines

- JSON to Kotlin plugin for generating Model classes for API 
    - the plugin lets us copy and paste JSON API response and automatically generates necessary classes and fields (for API response) 

- DiffUtil
    - instead of notifyDataSetChanged() in RecipesAdapter which is an overkill regarding performance because it's updating the list all over again without checking if new list of food recipes contains some recipes from the old list
    - will check and compare old list of food recipes with the new list and update only those recipes (views) which are new

- Kotlin Flow
    - stream of data that can be computed asynchronously
    - can emit multiple values sequentially, as opposed to suspend functions that return only a single value
    - 3 main entities involved in stream of data
        - Producer - produces data that is added to the stream
        - Intermediaries (optional) - can modify each value emitted into the strea or the stream itself
        - Consumer - consumes values from the stream
    - emited values must be of the same type
    - built on top of Coroutines and can provide multiple values
    - can stop for 2 reasons: coroutine that collects is cancelled or the producer is finished emitting items

    - used to send data from Dao interface to Repository and from Repository to ViewModel 
    

- LIBRARIES
    - [ShimmerRecyclerView](https://github.com/omtodkar/ShimmerRecyclerView)
        - library which provides pretty shimmer effect on [placeholder_row_layout.xml](https://github.com/laurakciic/foodie/blob/master/Foodie_RMA/app/src/main/res/layout/placeholder_row_layout.xml) while data is loading 

    - [Retrofit](https://square.github.io/retrofit/)
        - a typesafe HTTP client library
        - provides communication between client & server
        - one simple interface, inside which we can create multiple functions
        - each of the functions can represent a different HTTP request
        - RemoteDataSource uses Retrofit lib to fetch data from Spoonacular API

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

    - [Coil]()
        - image loading lib for Android backed by Kotlin Coroutines
        - fast, lightweight, easy to use

    - [ROOM]()
        - part of Android Architecture components that provides abstraction layer over SQLite to allow fluent database access while harnessing full power of SQLite
        - considered as a better approach for data persistance than SQLiteDatabase
        - makes it easier to work with SQLite database objects, decreasing the amount of boilerplate code and verifying SQL queries at compile-time
        - main components: 
            - Entity (table within a database), 
                - ROOM lib makes table for every class that has @Entity annotation
                - fields in the class correspond to columns in the table, therefore can be considered as small model classes with no logic
            - DAO (Data Access Object)
                - responsible for defining methods that access the database
                - will contain all SQL queries
                - there are already some predefined which can be accessed with @Insert, @Update, @Delete 
                - manually written queries have @Query
            - Database 
                - contains database holder, servers as the main access point for underlying connection to application's data 
        
        - main advantages: 
            - compile-time verification of SQL queries
                - each query and entity annotation is checked which preserves app from crash issues at run time
                - checks syntax but missing tables as well
            - less boiler plate code
            - easily integrated with other Architecture components like LiveData

        - LocalDataSource will cache API response and use it a first source of truth

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


