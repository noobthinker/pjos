package com.pj109.xkorey.share.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

open class Staff {

    companion object {
        private val propCache = ConcurrentHashMap<String, String>()
        private val observers = ConcurrentHashMap<String, MutableLiveData<String>>()

        @JvmStatic
        open fun cache(key: String, value: String) {
            propCache[key] = value
        }

        @JvmStatic
        open fun observerCache(key: String, putValue: String) {
            if(observers.contains(key)){
                observers[key]!!.postValue(putValue)
            }else{
                observers[key]=MutableLiveData()
                observers[key]!!.postValue(putValue)
            }
        }


        @JvmStatic
        open fun cache(key: String): String {
            propCache[key]?.let {
                return it
            }
            return ""
        }



        @JvmStatic
        open fun observerCache(key: String): LiveData<String> {
            if(observers.contains(key)){
                return observers[key]!!
            }else{
                observers[key] = MutableLiveData()
                return observers[key]!!
            }

        }

    }



}