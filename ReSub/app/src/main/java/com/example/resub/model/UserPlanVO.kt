package com.example.resub.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.michalsvec.singlerowcalendar.utils.DateUtils
import java.util.*

@Entity(tableName = "UserPlan")
class UserPlanVO {
    constructor()

    @PrimaryKey
    @ColumnInfo(name="plan_id")
    var plan_id : Int = -1

    @ColumnInfo(name="register_date")
    var register_date : String = ""

    @ColumnInfo(name="plan_data")
    var planVO : PlanVO? = null

    @ColumnInfo(name="plan_alarm")
    var alarm : Boolean = false

    constructor(id : Int, date: Date, planVO: PlanVO){
        this.plan_id = id
        setDate(date)
        this.planVO = planVO
        this.alarm = false
    }

    fun setDate(date : Date){
        this.register_date = "${DateUtils.getYear(date)}-${DateUtils.getMonthNumber(date)}-${DateUtils.getDayNumber(date)}"
    }
}