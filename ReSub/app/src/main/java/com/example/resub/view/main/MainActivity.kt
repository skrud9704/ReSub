package com.example.resub.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.resub.CustomBottomNavigation
import com.example.resub.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigation()
    }

    private fun setBottomNavigation(){
        custom_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu)
    }
}