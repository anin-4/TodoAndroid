package com.example.todoapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.todoapp.ui.viewModels.SortOrder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="todo_datastore")

    val preferenceFlow = context.dataStore.data
        .catch {exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {preferences ->
            val hideCompleted = preferences[PreferenceKeys.HIDE_COMPLETED]?:false

            val sortOrder = SortOrder.valueOf(
                preferences[PreferenceKeys.SORT_ORDER]?:SortOrder.BY_NAME.name
            )
            FilterPreferences(sortOrder,hideCompleted)
        }

    suspend fun updateSortOrder(sortOrder: SortOrder){
        context.dataStore.edit {settings ->
            settings[PreferenceKeys.SORT_ORDER]=sortOrder.name
        }
    }

    suspend fun updateHideCompleted(hide:Boolean){
        context.dataStore.edit {settings->
            settings[PreferenceKeys.HIDE_COMPLETED]=hide
        }
    }


    private object PreferenceKeys{
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val HIDE_COMPLETED = booleanPreferencesKey("hide_completed")
    }
}

data class FilterPreferences(val sortOrder: SortOrder, val hideCompleted:Boolean)