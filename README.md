
# 🥑 Foodie

Foodie is a Kotlin mobile application for browsing various recipes working with local and remote data sources, such as [Spoonacular API](https://spoonacular.com/food-api), & SQLite with ROOM on top of it.

## 📌 Navigation

1. [Data Structure](#data-structure)
2. [Spoonacular API & Retrofit](#spoonacular-api-and-retrofit)
3. [Database](#database)

Other   
[DetailsActivity](#details-activity)  
[Chips](#chips)  
[JSON to Kotlin plugin](#json-to-kotlin-plugin)   
[DiffUtil](#diffutil)   
[KotlinFlow](#kotlinflow)   
[StateFlow](#stateflow)

[ShimmerRecyclerView](#shimmerrecyclerview)   
[Dagger](#dagger)  
[Hilt](#hilt)   
[DataBinding](#data-binding)   
[Binding Adapters](#binding-adapters)
[Coil](#coil)  
[DataStore](#datastore)   
[Motion Layout](#motion-layout)

## 💡 Data Structure

RemoteDataSource: [Spoonacular API](https://spoonacular.com/food-api/pricing) with the help of [Retrofit](#retrofit) library    
- RemoteDataSource uses Retrofit to fetch data from API

LocalDataSource: [SQLite](#sqlite) with [ROOM](#room) on top of it which provides compile-time verification of SQL queries
- LocalDataSource will cache API response and use it a first source of truth

## Spoonacular API and Retrofit
### Retrofit
- a typesafe HTTP client library
- - RemoteDataSource uses Retrofit to fetch data from Spoonacular
- provides communication between client & server
- one simple interface, inside which we can create multiple functions
- each of the functions can represent a different HTTP request

## 📜 Database
### SQLite
- opensource SQL database that stores data to a text file on a device
- can manage low to medium-traffic HTTP requests
- used alongside with ROOM

### ROOM
Provides:
- compile-time verification of SQL queries
- annotations that minimize repetitive and error-prone boilerplate code

Has 3 components:
1. Entity
    - a table within the DB, must contain @Entity annotation
    - fields in the class correspond to columns in the table
    - entity classes tend to be small model classes

2. DAO (Data Access Object)
    - defines methods that access the database
    - contains all SQL queries
    - there are already some predefined which can be accessed with @Insert, @Update, @Delete
    - manually written queries have @Query

3. Database
    - contains database holder, servers as the main access point for underlying connection to application's data

Main advantages
- compile-time verification of SQL queries
- each query and entity annotation is checked which preserves app from crash issues at run time
- checks syntax but missing tables as well
- less boiler plate code
- easily integrated with other Architecture components like LiveData

<br/>

## Details activity
- holds 3 fragments (tabs): Overview, Ingredients, Instructions fragment
- not requesting data from API directly
>only from RecipesFragment data is requested directly from API
- we want to be able to send that result from API to DetailsActivity

<br/>

## Chips
- compact components that display discrete info
- flexible enough to be used for entering info, filtering content, selection and triggering actions
- mostly used in a group

Types from a design POV
1. input/entry (to a field)
2. choice (single choice selection in a group of 2 or more chips)
3. filter (multiple choice selection in a group of 2 or more chips) - checkmark icon on the left
>used in Foodie for MealType and DietType
4. action (used to trigger a given action, contain icon and a text label)

<br/>

## [JSON to Kotlin plugin](https://www.geeksforgeeks.org/integrating-jsontokotlin-plugin-with-android-studio/)
- generates Model classes for API
- the plugin lets us copy and paste JSON API response and automatically generates necessary classes and fields (for API response)

<br/>

## [DiffUtil](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/DiffUtil)
- instead of notifyDataSetChanged() in RecipesAdapter which is an overkill regarding performance because it's updating the list all over again without checking if new list of food recipes contains some recipes from the old list
- will check and compare old list of food recipes with the new list and update only those recipes (views) which are new

<br/>

## [KotlinFlow](https://developer.android.com/kotlin/flow)
- used to send data from DAO interface to Repository and from Repository to ViewModel

- stream of data that can be computed asynchronously
- can emit multiple values sequentially, as opposed to suspend functions that return only a single value

3 main entities involved in stream of data   
- Producer - produces data that is added to the stream   
- Intermediaries (optional) - can modify each value emitted into the stream or the stream itself   
- Consumer - consumes values from the stream

- emitted values must be of the same type
- built on top of Coroutines and can provide multiple values
- can stop for 2 reasons: coroutine that collects is cancelled or the producer is finished emitting items

## [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- part of KotlinFlowAPI that enables Flow to optimally emit state updates and emit values to multiple consumers

2 implementations
1. StateFlow (ReadOnly) - always active and in memory (represents hot stream of data)  
   - requires an initial state to be passed in it's constructor (LiveData does not)   
   - also LiveData.observe() automatically unregisters the consumer when the view goes to the STOPPED state, whereas collecting from a StateFlow or any other flow does not   
   - represents hot stream od data ()
2. MutableStateFlow

<br/>

## [ShimmerRecyclerView](https://github.com/omtodkar/ShimmerRecyclerView)
- library which provides pretty shimmer effect on [placeholder_row_layout.xml](https://github.com/laurakciic/foodie/blob/master/Foodie_RMA/app/src/main/res/layout/placeholder_row_layout.xml) while data is loading

<br/>

## [Dagger](https://developer.android.com/training/dependency-injection/dagger-basics)
- [dependency injection](https://developer.android.com/training/dependency-injection) library for Java, Kotlin & Android
- manages dependencies which results in limiting project's complexity as it scales up
- automatically generates code that mimics the code you would otherwise have hand-written
- provides fully static and compile-time dependencies addressing many of the dev & performance issues of reflection-based solutions (such as [Guice](https://en.wikipedia.org/wiki/Google_Guice))
- because the code is generated at compile time, it's traceable and more performant than other reflection-based solutions such as Guice

<br/>

## [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- dependency injection library for Android
- built on top of Dagger so it can reduce complexity even more
- provides a standard way to incorporate Dagger dependency injection into Android app
- defines a standard way to do DI by providing containers for every Android class in project and managing their lifecycles automatically
- built on top of Dagger to benefit from compile time correctness, runtime performance, scalability and Android Studio support

<br/>

## [Data Binding](https://developer.android.com/topic/libraries/data-binding)
- support library which makes possible to bind data to views in declarative format using XML layouts instead of setting them programmatically
- generates new class behind the scenes
    - ex. for fragment_main.xml -> FragmentMainBinding class will be generated
    - new class can be used to bind actual data from UI with an actual variable, from activities/fragments

    - converts any XML layout file to Data Binding Layout
        - root element on that layout file becomes layout element
        - that layout element will contain all other views inside
        - we can also create diff variables that can easily be used to set data directly on views
        - helps to deal with findViewById in more elegant way to reduce boiler plate code & has many other benefits


## [Binding Adapters](https://developer.android.com/topic/libraries/data-binding/binding-adapters)
- used to create custom binding attribute for views inside layout XML file

<br/>

## [Coil](https://coil-kt.github.io/coil/)
- image loading lib for Android backed by Kotlin Coroutines
- fast, lightweight, easy to use

<br/>

## [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=CjwKCAjwg5uZBhATEiwAhhRLHi_47mH-jHnz66jNbx4ZWer4z-LgWLXf4B7o4U4PWhW7F7kPE0uswRoCYS4QAvD_BwE&gclsrc=aw.ds)
- data storage solution replacing Shared Preferences (provides synchronous API, not main-thread safe)
- safe to use in UI thread because it uses Dispatchers.IO under the hood (main-thread safe)
- no run-time exceptions
- uses Kotlin Coroutines and Flow to store data

2 different types of implementation to store data
1. Preference DataStore - uses key & value pairs to store data, does not provide type safety
2. Proto Datastore      - stores data as a custom type with specified schema using protocol buffers

<br/>

## [Motion Layout](https://developer.android.com/develop/ui/views/animations/motionlayout)

Needs Constraint Layout to convert it to Motion Layout

1. in fragment_overview.xml go to design & select Constraint Layout, convert it to Motion Layout
>This creates new dir xml in which is fragment_overview_scene.xml which will contain all animations

>2 constraint sets
>- start (start position of the view) & end

2. select end endpoint, select main_imageView & set its layout height to 1dp (in scene xml code will be generated)

3. create swipe handler (transition: start->end, drag direction: dragUp, anchor side: bottom, anchor id: imageView)

4. select arrow that represents transition

5. create key frames (attributes) on time & likes textView, time & likes imageView
    - position: 100, attribute: alpha
    - select that attribute and set alpha to 0

<br/>

## Obstacles & Solutions

### 1. NavHostFragment couldn't be found (app crashing with clean debug, no errors)

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






