package com.example.resub.view.main

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.resub.R
import com.example.resub.database.RoomDB
import com.example.resub.model.AppVO
import com.example.resub.view.TestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private lateinit var viewPager2 : ViewPager2
    private lateinit var userApps : List<AppVO>
    private val icons : ArrayList<Drawable> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUserApp()
        setRecyclerList()
        setBottomNavigation()

        btn_menu.setOnClickListener {
            val nextIntent = Intent(this,TestActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private fun getUserApp(){
        val roomDB = RoomDB.getInstance(this)
        userApps = roomDB.appDAO().getAppList()

        val pakageManager : PackageManager = packageManager
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resloveInfos : List<ResolveInfo> = pakageManager.queryIntentActivities(intent,0)
        for(installapp in resloveInfos){
            for(app in userApps){
                if(app.app_package==installapp.activityInfo.packageName){
                    icons.add(installapp.activityInfo.loadIcon(packageManager))
                    break
                }
            }
        }
    }

    private fun setBottomNavigation(){
        custom_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu)
    }

    private fun setRecyclerList(){
        viewPager2 = apps_pager
        // MyRecyclerViewAdapter is an standard RecyclerView.Adapter :)
        viewPager2.adapter = MainAdapter(this,userApps,icons)

        // You need to retain one page on each side so that the next and previous items are visible
        viewPager2.offscreenPageLimit = 1

        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        viewPager2.setPageTransformer(pageTransformer)

        // The ItemDecoration gives the current (centered) item horizontal margin so that
        // it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            this,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPager2.addItemDecoration(itemDecoration)
    }


}