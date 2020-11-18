package com.example.resub.network

import com.example.resub.model.AppVO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {

    @FormUrlEncoded
    @POST("Apps/load")
    fun loadServiceApps() : Call<ArrayList<AppVO>>

}