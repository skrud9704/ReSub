package com.example.resub.network

import com.example.resub.model.AppVO
import com.example.resub.model.PlanVO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {

    @POST("Apps/load")
    fun loadServiceApps() : Call<ArrayList<AppVO>>

    @FormUrlEncoded
    @POST("Apps/plan")
    fun loadAppPlans(
        @Field("app_package") app_package : String
    ) : Call<ArrayList<PlanVO>>

}