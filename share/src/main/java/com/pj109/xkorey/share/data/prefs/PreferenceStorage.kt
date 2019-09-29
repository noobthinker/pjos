package com.pj109.xkorey.share.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var onboardingCompleted: Boolean
    var onnetworkEnable: Boolean
    var onSocketEnable:Boolean
    var onPropertiesInit:Boolean
    var functionKey:String?
    var nettyClientWork:Boolean
    var selectedFilters: String?
}

class SharedPreferenceStorage @Inject constructor(context: Context) :
    PreferenceStorage {

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var onboardingCompleted by BooleanPreference(prefs, PREF_ONBOARDING, false)


    override var onnetworkEnable by BooleanPreference(prefs, PREF_networkEnable, false)

    override var onSocketEnable by BooleanPreference(prefs,PREF_SOCKETON,false)

    override var onPropertiesInit by BooleanPreference(prefs,PREF_PROP_INIT,false)

    override var functionKey: String? by StringPreference(prefs,PREF_FUN_KEY,"")

    override var nettyClientWork by BooleanPreference(prefs,PREF_NETTY_On,false)

    override var selectedFilters by StringPreference(prefs, PREF_TAG_FILTERS, null)

    companion object {
        const val PREFS_NAME = "pjos"
        const val PREF_ONBOARDING = "pref_onboarding"
        const val PREF_networkEnable = "pref_network_enable"
        const val PREF_SOCKETON = "pref_socket_on"
        const val PREF_PROP_INIT="pref_properties_init"
        const val PREF_FUN_KEY="pref_properties_function_key"
        const val PREF_NETTY_On="pref_properties_netty_on"
        const val PREF_TAG_FILTERS="pref_Tag_filters"
    }

}


class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit { putBoolean(name, value) }
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.edit { putString(name, value) }
    }
}