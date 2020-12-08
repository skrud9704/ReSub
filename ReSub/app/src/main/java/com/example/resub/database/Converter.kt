package com.example.resub.database

import androidx.room.TypeConverter
import com.example.resub.model.PlanVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converter {
    @TypeConverter
    fun fromPlanVOToJson(planVO: PlanVO?): String? {
        return Gson().toJson(planVO)
    }

    @TypeConverter
    fun fromJsonToPlanVO(value: String?): PlanVO? {
        val planVO = object : TypeToken<PlanVO?>() {}.type
        return Gson().fromJson(value,planVO)
    }
}

