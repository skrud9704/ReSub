package com.example.resub.view

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.resub.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pakageManager : PackageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resloveInfos : List<ResolveInfo> = pakageManager.queryIntentActivities(intent,0)

        app_total.text = "총 ${resloveInfos.size}개"
        val mainAdapter = MainAdapter(packageManager,this,resloveInfos)
        app_list.adapter = mainAdapter


    }
}