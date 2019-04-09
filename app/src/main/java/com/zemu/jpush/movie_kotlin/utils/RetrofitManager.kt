package com.zemu.jpush.movie_kotlin.utils


import android.content.Context
import android.content.SharedPreferences
import android.util.Log


import com.zemu.jpush.movie_kotlin.app.MyApplication

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 工具类在使用的时传入context
 */
class RetrofitManager private constructor() {

    private val mRetrofit: Retrofit


    object SINGLE_INSTANCE {
        val MCONTEXT = MyApplication.mContext
        internal val INSTANCE = RetrofitManager()
    }

    init {
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttpClient())
                .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpCacheDirectory = File("/sdcard", "cache_xx")

        //Cache cache  = new Cache(this.getCacheDir(), 10240 * 1024);
        val cache = Cache(httpCacheDirectory, (1024 * 1024 * 10).toLong())
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(UserAgentInterceptor())
                .addNetworkInterceptor { chain ->
                    val request = chain.request()
                    Log.e("MainActivity", "新请求 =request==$request")

                    val response = chain.proceed(request)
                    val response_build: Response
                    if (NetworkUtils.isNetWorkAvailable(SINGLE_INSTANCE.MCONTEXT)) {

                        val maxAge = 60 * 60 * 24 // 有网络的时候从缓存1天后失效
                        response_build = response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, max-age=$maxAge")
                                .build()
                    } else {
                        val maxStale = 60 * 60 * 24 * 28 // // 无网络缓存保存四周
                        response_build = response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                                .build()
                    }

                    response_build
                }
                .cache(cache)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
    }

    fun <T> create(tClass: Class<T>): T {
        return mRetrofit.create(tClass)
    }

    /**
     * 封装公共请求头的自定义拦截器
     */
    private inner class UserAgentInterceptor internal constructor() : Interceptor {

        private val sp: SharedPreferences

        init {
            sp = SINGLE_INSTANCE.MCONTEXT.getSharedPreferences("config", Context.MODE_PRIVATE)
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val url = request.url().toString()
            if (!url.contains("registerUser") || !url.contains("login")) {
                // 如果包含的话就是注册或登录,这里是非注册or登录
                val userId = sp.getInt("userId", 0)
                val sessionId = sp.getString("sessionId", "")
                Log.i("RetrofitManager", "userId:  --- $userId;     sessionId:  ---$sessionId")
                val builder = request.newBuilder()
                val headerBuilder = request.headers().newBuilder()

                headerBuilder.add("sessionId", sessionId!!)
                val headers = headerBuilder.add("userId", userId.toString())
                        .build()
                builder.headers(headers)
                request = builder.build()
            }
            return chain.proceed(request)
        }
    }

    companion object {
        private val BASE_URL = "http://mobile.bwstudent.com/movieApi/"

        val instance: RetrofitManager
            get() = SINGLE_INSTANCE.INSTANCE
    }


}


