package com.example.resub.view.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.resub.R
import com.example.resub.database.RoomDB
import com.example.resub.model.AppVO
import com.example.resub.network.RetrofitClient
import com.example.resub.network.RetrofitService
import com.example.resub.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private lateinit var installedApps : List<ResolveInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getLocalAppData()
        getServerAppData()
    }

    // 설치된 앱 정보 가져오기
    private fun getLocalAppData(){
        progress.visibility = View.VISIBLE
        val pakageManager : PackageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        installedApps = pakageManager.queryIntentActivities(intent,0)

    }

    // 서버로부터 구독서비스를 제공하는 앱 정보 가져오기
    private fun getServerAppData(){
        val retrofitClient = RetrofitClient.getInstance()
        val retrofitService = retrofitClient.create(RetrofitService::class.java)
        retrofitService.loadServiceApps().enqueue(object : Callback<ArrayList<AppVO>>{
            override fun onFailure(call: Call<ArrayList<AppVO>>, t: Throwable) {
                Log.d(TAG,"실패 : $t")
            }

            override fun onResponse(call: Call<ArrayList<AppVO>>, response: Response<ArrayList<AppVO>>) {
                Log.d(TAG,"성공")
                if(response.body()!!.isNotEmpty()){
                    // 사용자의 앱과 비교해서 로컬DB에 넣기
                    insertUserApp(response.body()!!)
                }else{
                    Log.d(TAG,"받아온 데이터 없음.")
                }
            }

        })
    }

    // 사용자 단말에 설치된 앱과 서버에서 받아온 앱의 데이터를 비교해 일치하면 로컬 DB에 insert
    private fun insertUserApp(serverData : ArrayList<AppVO>){
        val room = RoomDB.getInstance(this)

        for(resolveInfo in installedApps){
            for(app in serverData){
                // 패키지명이 서로 같으면 insert App
                if(app.app_package==resolveInfo.activityInfo.packageName)
                    room.appDAO().insertApp(app)
            }
        }

        progress.visibility = View.GONE
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}