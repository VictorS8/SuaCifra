package br.com.suacifra.database

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.com.suacifra.utils.Config

object SharedPreferencesSingleton {

    private fun getSharedPreferences(applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )
    }

    fun editor(applicationContext: Context, dataKey: String, data: Any) {
        val sharedPreferencesEditor = getSharedPreferences(applicationContext).edit()
        when (data) {
            is Int -> {
                sharedPreferencesEditor.putInt(dataKey, data)
            }
            is String -> {
                sharedPreferencesEditor.putString(dataKey, data)
            }
            is Boolean -> {
                sharedPreferencesEditor.putBoolean(dataKey, data)
            }
        }
        sharedPreferencesEditor.apply()
    }

    fun getData(applicationContext: Context, dataKey: String, nullData: Any): Any {
        return when (nullData) {
            is Int -> getSharedPreferences(applicationContext).getInt(dataKey, nullData)
            is String -> getSharedPreferences(applicationContext).getString(dataKey, nullData)
                ?: nullData
            is Boolean -> getSharedPreferences(applicationContext).getBoolean(dataKey, nullData)
            else -> {
                // TODO - Retirar antes de fazer push
                Log.w("GetData", "Else on Get Data is being used")
            }
        }
    }

    fun mutableSetEditor(applicationContext: Context, dataKey: String, data: MutableSet<String>) {
        val sharedPreferencesEditor = getSharedPreferences(applicationContext).edit()
        sharedPreferencesEditor.putStringSet(dataKey, data)
        sharedPreferencesEditor.apply()
    }

    fun getMutableSetData(
        applicationContext: Context,
        dataKey: String,
        data: MutableSet<String>
    ): MutableSet<String> {
        return getSharedPreferences(applicationContext).getStringSet(dataKey, data) ?: data
    }

}
