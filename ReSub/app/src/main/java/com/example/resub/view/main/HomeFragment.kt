package com.example.resub.view.main

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.resub.R
import com.example.resub.database.RoomDB
import com.example.resub.model.AppVO
import com.example.resub.util.AppConstants
import com.example.resub.view.register.RegisterActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    private lateinit var viewPager2 : ViewPager2
    private lateinit var userApps : List<AppVO>
    private val icons : ArrayList<Drawable> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        // 처리
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserApp()
        setRecyclerList()

        fab.setOnClickListener {
            if(viewPager2.currentItem!=userApps.size){
                val nextIntent = Intent(activity!!.applicationContext, RegisterActivity::class.java)
                nextIntent.putExtra(AppConstants.INTENT_EXTRA_APPVO,userApps[viewPager2.currentItem])
                startActivity(nextIntent)
            }
        }
    }

    private fun getUserApp(){
        val roomDB = RoomDB.getInstance(activity!!.applicationContext)
        userApps = roomDB.appDAO().getAppList()

        val pakageManager : PackageManager = activity!!.packageManager
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resloveInfos : List<ResolveInfo> = pakageManager.queryIntentActivities(intent,0)
        for(installapp in resloveInfos){
            for(app in userApps){
                if(app.app_package==installapp.activityInfo.packageName){
                    icons.add(installapp.activityInfo.loadIcon(pakageManager))
                    break
                }
            }
        }
    }

    private fun setRecyclerList(){
        viewPager2 = apps_pager
        // MyRecyclerViewAdapter is an standard RecyclerView.Adapter :)
        viewPager2.adapter = MainAdapter(activity!!.applicationContext,userApps,icons)

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
            activity!!.applicationContext,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPager2.addItemDecoration(itemDecoration)
    }
}