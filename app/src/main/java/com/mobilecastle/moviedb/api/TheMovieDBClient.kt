package de.mobilecastle.moviedb.api

import de.mobilecastle.moviedb.utilities.AppConstants.API_KEY
import de.mobilecastle.moviedb.utilities.AppConstants.BASE_URL
import de.mobilecastle.moviedb.utilities.AppConstants.TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TheMovieDBClient {

    fun getClient(): TheMovieDBInterface{

        val requestInterceptor = Interceptor { chain ->
            // Interceptor can take only one argument which is a lambda function so
            // parenthesis can be omitted

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            //explicitly return a value from whit @ annotation. lambda always returns
            // the value of the last expression implicitly
            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)
    }
}