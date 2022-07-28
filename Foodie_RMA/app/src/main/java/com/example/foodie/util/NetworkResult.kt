package com.example.foodie.util

// will handle responses from API
// will contain 3 diff classes
// sealed class
// generics are used to create this class <T>

sealed class NetworkResult<T>(
    val data: T? = null,            //  1.par: data from API (generics type that's nullable)
    val message: String? = null     //  2.par: message       (string type, also nullable)
) {

    class Success<T>(data: T): NetworkResult<T>(data)      // returns NetworkResult of type T (or generics), data is passed inside
    class Error<T>(message: String?, data: T? = null): NetworkResult<T>(data, message)  // when there's an error, data is null
    class Loading<T>: NetworkResult<T>()
}