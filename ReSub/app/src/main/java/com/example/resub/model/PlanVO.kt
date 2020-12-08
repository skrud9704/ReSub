package com.example.resub.model

import com.google.gson.annotations.SerializedName

class PlanVO {
    constructor()

    @SerializedName("plan_id")  // 변경금지
    var plan_id : Int = -1

    @SerializedName("plan_name")  // 변경금지
    var plan_name : String = ""

    @SerializedName("plan_charge")  // 변경금지
    var plan_charge : String = ""

    @SerializedName("plan_period")  // 변경금지
    var plan_period : String = ""

    @SerializedName("plan_benefit")  // 변경금지
    var plan_benefit : String = ""

    @SerializedName("plan_app_package")  // 변경금지
    var plan_app_package : String = ""

    constructor(id:Int, name : String, charge : String, period : String, benefit : String, plan_app_package:String) : this() {
        this.plan_id = id
        this.plan_name = name
        this.plan_charge = charge
        this.plan_period = period
        this.plan_benefit = benefit
        this.plan_app_package = plan_app_package
    }

    fun getFullPeriod() : PlanVO{
        val changedUnit = when(this.plan_period.last()){
            'm' -> "month"
            'w' -> "week"
            'd' -> "day"
            'y' -> "year"
            else -> this.plan_period.last().toString()
        }
        this.plan_period = this.plan_period.substring(0,this.plan_period.length-1).plus(changedUnit)

        return this
    }


}