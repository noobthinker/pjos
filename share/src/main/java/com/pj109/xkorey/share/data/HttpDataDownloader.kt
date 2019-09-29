package com.pj109.xkorey.share.data

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import com.pj109.xkorey.model.data.Request
import com.pj109.xkorey.model.data.Response
import com.pj109.xkorey.model.enums.HttpMethod
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class HttpDataDownloader @Inject constructor (
    val context: Context):DataDownloader {

    @Throws(IOException::class)
    @WorkerThread
    override fun <T : Response> load(request: Request, clazz: Class<T>): T {
        return when (request.method) {
            HttpMethod.POST -> post(request, clazz)
            else -> fetch(request, clazz)
        }
    }

    private fun <T> fetch(parameters: Request,clazz:Class<T>):  T where T:Response {
        Timber.d("request start. ${parameters.url}")
        val httpBuilder = HttpUrl.parse(parameters.url)?.newBuilder()
            ?: throw IllegalArgumentException("Malformed Session data URL")

        parameters.params!!.forEach {
            httpBuilder.addQueryParameter(it.key, it.value.toString())
        }
        var head: Headers? = null
        val build = okhttp3.Request.Builder()
        parameters.header?.let {
            head = Headers.of(it)
            build.headers(head)
        }
        val request = build.url(httpBuilder.build())
        val response = client.newCall(request.build()).execute()
        val json = response.body()!!.string()
        return call(json,clazz)
    }

    private fun <T> post(parameters: Request,clazz:Class<T>):  T where T:Response{
        val bodyBuilder: FormBody.Builder = FormBody.Builder()
        parameters.params!!.forEach {
            bodyBuilder.add(it.key, it.value.toString())
        }
        val build = okhttp3.Request.Builder()
        var head: Headers? = null
        parameters.header?.let {
            head = Headers.of(it)
            build.headers(head)
        }
        val request = build.url(HttpUrl.parse(parameters.url)).post(bodyBuilder.build())
        val response = client.newCall(request.build()).execute()
        val json = response.body()!!.string()
        return call(json,clazz)
    }



    private fun <T> call(json: String,clazz:Class<T>):  T where T: Response {
        Timber.i("json : ${json}")
        val res = gson.fromJson(json, clazz)
        Timber.i("response code ${res.code}")
        return res
    }


    private val gson = GsonBuilder()
        .create()
    private val client: OkHttpClient by lazy {
        val logInterceptor = HttpLoggingInterceptor()
        val protocols = arrayListOf(Protocol.HTTP_1_1, Protocol.HTTP_2)
        val cacheSize = 512L * 1024// 512k
        val cacheDir = context.getDir("cache_data", Context.MODE_PRIVATE)
        val cache = Cache(cacheDir, cacheSize)
        OkHttpClient.Builder()
            .protocols(protocols)
            .cache(cache)
            .addInterceptor(logInterceptor)
            .build()
    }


}