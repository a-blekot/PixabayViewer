package com.example.pixabayviewer.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api/")
    suspend fun getImages(
        @Query("key") key: String,
        @Query("page") page: Int
    ): PixabayResponse

    @GET("api/")
    suspend fun getImage(
        @Query("key") key: String,
        @Query("id") id: Int
    ): PixabayResponse
}
