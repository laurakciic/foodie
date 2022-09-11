package com.example.foodie.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodie.util.Constants.Constants.DEFAULT_DIET_TYPE
import com.example.foodie.util.Constants.Constants.DEFAULT_MEAL_TYPE
import com.example.foodie.util.Constants.Constants.PREFERENCES_BACK_ONLINE
import com.example.foodie.util.Constants.Constants.PREFERENCES_DIET_TYPE
import com.example.foodie.util.Constants.Constants.PREFERENCES_DIET_TYPE_ID
import com.example.foodie.util.Constants.Constants.PREFERENCES_MEAL_TYPE
import com.example.foodie.util.Constants.Constants.PREFERENCES_MEAL_TYPE_ID
import com.example.foodie.util.Constants.Constants.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

// main diff between DataStore & Shared Preferences is that DataStore is running on a background thread
// injected ApplicationContext
@ViewModelScoped  // Hilt lib annotation, because this class will be used inside RecipesViewModel
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    // saving fields which we passed inside fun parameters and saving them inside DataStore preference using keys
    // takes values from parameters and store it inside DataStore preference using keys we specified above
    suspend fun saveMealAndDietType(    // edit is a suspend fun and that's why DataStore is using Kotlin coroutines to run on a background thread
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->     // mutable preferences obj
                                // key name                 value which is passed through this fun
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    // save the value from parameters to it's preference key
    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            // calling pref, passing key and storing value from function params inside this key
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    // read DataStore to get values we stored
    // when reading values from BottomSheet we're using Flow to pass data class MealAndDietType which has 4 diff fields
    // var readMealAndDietType type Flow which contains MealAndDietType obj
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
            // retrieving values from DataStore preferences using specific keys we defined
            // if there's no data inside specific keys, return default values
        .map { preferences ->
            // 4 diff var, 1 MealAndDietType obj and emit it using Flow

            // storing value inside var selectedMealType
            // selecting the val of key selectedMealType and if there's no values saved already (inside DataStore for that spec key), then emit (return) main course
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(        // obj
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    // var to read the data from DataStore
    // return the value of backOnline key
    val readBackOnline: Flow<Boolean> = dataStore.data  // returning Flow of Boolean (backOnline)
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->   // preferences to get the key, return the value of backOnline key
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false // if there's no value, default value is false
            backOnline
        }

}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)