package com.example.resub.database

import androidx.room.*
import com.example.resub.model.AppVO
import com.example.resub.model.UserPlanVO

interface RoomDAO<ET> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : ET)
}

@Dao
interface AppDAO : RoomDAO<AppVO> {
    // 키 겹칠때 지금 넣는것으로 대체
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertApp(app : AppVO) : Long

    @Query("SELECT * FROM UserApp")
    fun getAppList() : List<AppVO>

}

@Dao
interface UserPlanDAO : RoomDAO<UserPlanVO> {
    // 키 겹칠때 지금 넣는것으로 대체
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertPlan(plan : UserPlanVO) : Long

    @Query("SELECT * FROM UserPlan")
    fun getPlanList() : List<UserPlanVO>

    @Query("UPDATE UserPlan SET plan_alarm=:alarm")
    fun updatePlanAlarm(alarm : Boolean) : Long

}