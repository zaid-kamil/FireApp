package com.digi.fireapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://www.googleapis.com/books/v1/"
private const val QUERY_PARAM = "q"
private const val MAX_RESULTS = "maxResults"
private const val PRINT_TYPE = "printType"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
//    @GET("volumes?")
//    suspend fun getBook(
//        @Query("q") searchString: String,
//        @Query("maxResults") maxResults: String,
//        @Query("printType") printType: String,
//    ): BookResponse
}

object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}

// sample viewmodel calling
// BookApi.retrofitService.getBook("android", "5", "books")