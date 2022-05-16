package com.example.circularplayground.data.network

import com.example.circularplayground.data.network.dto.AccountStatusDto
import retrofit2.Response
import retrofit2.http.GET

interface CircularService {

    @GET("endpoint.json")
    suspend fun getAccountStatus(): Response<AccountStatusDto>
}