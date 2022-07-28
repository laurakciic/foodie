package com.example.foodie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// the @ generates code behind the scenes (Dagger components)
@HiltAndroidApp
class MyApplication: Application() {
}