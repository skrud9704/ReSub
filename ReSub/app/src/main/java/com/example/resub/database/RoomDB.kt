package com.example.resub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.resub.model.AppVO
import com.example.resub.model.UserPlanVO

@Database(version = 1, entities = [AppVO::class, UserPlanVO::class], exportSchema = false)
//@TypeConverters(Converter::class)
abstract class RoomDB : RoomDatabase(){

    abstract fun appDAO() : AppDAO
    abstract fun userPlanDAO() : UserPlanDAO

    // 싱글톤
    companion object{
        private var instance : RoomDB ? = null

        @Synchronized
        fun getInstance(context: Context) : RoomDB{
            if(instance==null){
                instance = Room.databaseBuilder(context.applicationContext, RoomDB::class.java,"resub_local_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

        fun destroyInstance(){
            instance = null
        }
    }
}