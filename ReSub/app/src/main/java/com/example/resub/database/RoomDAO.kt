package com.example.resub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

}