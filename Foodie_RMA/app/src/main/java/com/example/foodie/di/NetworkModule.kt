package com.example.foodie.di

import com.example.foodie.data.network.FoodRecipesAPI
import com.example.foodie.util.Constants.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// tells Hilt how to provide Retrofit builder with RemoteDataSource

// in order to provide FoodRecipesAPI we need to satisfy Retrofit dependency
// in order to satisfy Retrofit dependency we need to provide okHttpClient and gsonConverterFactory
// return types of those 2 fun are telling Hilt lib that we have specified how to create an instance
//      of those dependencies

@Module
@InstallIn(SingletonComponent::class)
// all bindings inside NetworkModule will be available in SingletonComponent

object NetworkModule {

    // with provideHttpClient fun we have satisfied that dependency in fun provideRetrofitInstance

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    // with provideConverterFactory fun we have satisfied that dependency in fun provideRetrofitInstance

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    // Retrofit instance needed to satisfy ApiService (fun provideApiService)
    // return type of provideRetrofitInstance is Retrofit and that's how Hilt knows
    //      that we have specified dependency to satisfy ApiService binding as well

    @Singleton
    @Provides
    fun provideRetrofitInstance (                       // needs to satisfy 2 dependencies
        okHttpClient: OkHttpClient,                     // specifying how to provide instance of okHttpClient
        gsonConverterFactory: GsonConverterFactory      // and gsonConverterFactory, ex. Hilt will know how to find gsonConverterFactory dependency through return type of a fun
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)                          // BASE_URL from Constants class
            .client(okHttpClient)                       // already provided in parameteres
            .addConverterFactory(gsonConverterFactory)  // also in parameteres
            .build()
    }

    // by specifying class FoodRecipesAPI as a return type
    // we are telling Hilt library which class we want to inject later

    @Singleton  // using app scope for FoodRecipesAPI, means we will have only one instance for each of those dependencies
    @Provides   // because we're using Retrofit library which is an external library (not created by us)
    fun provideApiService(retrofit: Retrofit): FoodRecipesAPI {     // returns FoodRecipesAPI
        return retrofit.create(FoodRecipesAPI::class.java)          //   which we will inject in RemoteDataSource
    }
}