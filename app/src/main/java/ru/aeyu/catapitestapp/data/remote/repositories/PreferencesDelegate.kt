package ru.aeyu.catapitestapp.data.remote.repositories

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PreferencesDelegate<TValue>(
    private val preferences: SharedPreferences,
    private val key_preference_name: String,
    private val defValue: TValue
) : ReadWriteProperty<Any?, TValue> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): TValue =
        with(preferences) {
            return when (defValue) {
                is Boolean -> (getBoolean(key_preference_name, defValue) as? TValue) ?: defValue
                is Int -> (getInt(key_preference_name, defValue) as TValue) ?: defValue
                is Float -> (getFloat(key_preference_name, defValue) as TValue) ?: defValue
                is Long -> (getLong(key_preference_name, defValue) as TValue) ?: defValue
                is String -> (getString(key_preference_name, defValue) as TValue) ?: defValue
                else -> throw NotFoundRealizationException(defValue)
            }
        }


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: TValue) =
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(key_preference_name, value)
                is Int -> putInt(key_preference_name, value)
                is Float -> putFloat(key_preference_name, value)
                is Long -> putLong(key_preference_name, value)
                is String -> putString(key_preference_name, value)
                else -> throw NotFoundRealizationException(value)
            }
            apply()
        }


    class NotFoundRealizationException(defValue: Any?) :
        Exception("not found realization for $defValue")
}
