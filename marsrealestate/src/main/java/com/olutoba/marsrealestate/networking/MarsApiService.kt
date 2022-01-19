package com.olutoba.marsrealestate.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/** The @GET annotation indicates that the "realestate" endpoint will be
requested with the GET HTTP method */
interface MarsApiService {
    @GET("realestate")
    suspend fun getProperties(): List<MarsProperty>
}

object MarsApi {
    val retrofitApiService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

