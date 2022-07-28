package com.example.foodie.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

// RemoteDataSource and LocalDataSource are injected here
// Repository will be injected inside ViewModel
// @ActivityRetainedScoped so Repository can survive conf change as well (since it will be injected in ViewModel)

@ActivityRetainedScoped                 // scope ann for bindings that should exist inside the life of an activity and survive conf change as well
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
){

    val remote = remoteDataSource       // so we can access it inside ViewModel

}