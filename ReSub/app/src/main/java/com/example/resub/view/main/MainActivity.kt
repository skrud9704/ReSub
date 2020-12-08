package com.example.resub.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.resub.R
import com.example.resub.view.TestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment())

        custom_bottom_nav.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.action_home->{
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_user->{
                    replaceFragment(UserFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else ->{
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        setBottomNavigation()

        btn_menu.setOnClickListener {
            val nextIntent = Intent(this,TestActivity::class.java)
            startActivity(nextIntent)
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

    private fun setBottomNavigation(){
        custom_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu)
    }


}