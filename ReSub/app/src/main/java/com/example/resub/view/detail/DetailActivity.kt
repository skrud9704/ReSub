package com.example.resub.view.detail

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.resub.R
import com.example.resub.model.AppVO
import com.example.resub.util.AppConstants
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    private lateinit var appVO : AppVO
    private val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        getData()
        setView()
    }

    private fun getData(){
        if(intent.hasExtra(AppConstants.INTENT_EXTRA_APPVO)){
            appVO = intent.getParcelableExtra<AppVO>(AppConstants.INTENT_EXTRA_APPVO)
        }else{
            Toast.makeText(this,"구독데이터 가져오기 실패",Toast.LENGTH_SHORT).show()
            Log.d(TAG,"AppVO Intent 없음")
            finish()
        }

    }

    private fun setView(){
        // 앱 아이콘
        val packageManager = this.packageManager
        val info : PackageInfo = packageManager.getPackageInfo(appVO.app_package,0)
        try{
            detail_app_icon.setImageDrawable(info.applicationInfo.loadIcon(packageManager))
        }catch (e : PackageManager.NameNotFoundException){
            Log.d(TAG,"App Icon 정보 가져오기 실패")
        }
        // 앱 이름
        detail_app_name.text = appVO.app_label
        // 앱 무료체험
        detail_app_free_period.text = if(appVO.app_free==1) "무료체험 유" else "무료체험 무"
        // 앱 첫 할인
        detail_app_discount_period.text = if(appVO.app_discount==1) "첫 할인 유" else "첫 할인 무"
        // 다중디바이스 지원
        detail_app_multi_device.text = if(appVO.app_multidevice==1) "다중 디바이스지원 유" else "다중 디바이스지원 무"
    }
}