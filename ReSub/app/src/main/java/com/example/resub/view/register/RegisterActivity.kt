package com.example.resub.view.register

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.resub.R
import com.example.resub.database.RoomDB
import com.example.resub.model.AppVO
import com.example.resub.model.PlanVO
import com.example.resub.model.UserPlanVO
import com.example.resub.network.RetrofitClient
import com.example.resub.network.RetrofitService
import com.example.resub.util.AppConstants
import com.example.resub.view.main.MainActivity
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.calendar_unselected_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RegisterActivity : AppCompatActivity(){
    private lateinit var appVO : AppVO
    private val planList : ArrayList<PlanVO> = arrayListOf()
    private val TAG = this::class.java.simpleName
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private lateinit var onPlanItemClick : OnPlanItemClick

    private var selectedPlan : PlanVO? = null
    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getData()
        setView()
        setPlanList()
        setCalender()

        onPlanItemClick = object : OnPlanItemClick{
            // 인터페이스 onPlanItemClick의 메서드
            override fun onClick(planVO: PlanVO) {
                selectedPlan = planVO
                selected_plan.text = planVO.plan_name
                if(selectedDate!=null){
                    register_result.visibility = View.VISIBLE
                    setResultView()
                }
            }

        }

        // 나의 구독에 추가.
        register_btn.setOnClickListener {
            if(selectedDate!=null && selectedPlan!=null){
                val roomDB = RoomDB.getInstance(this)
                roomDB.userPlanDAO().insertPlan(UserPlanVO(selectedPlan!!.plan_id,selectedDate!!,selectedPlan!!))
                Toast.makeText(this,"추가됐습니다!",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else
                Toast.makeText(this,"플랜과 날짜를 모두 선택해주세요.",Toast.LENGTH_SHORT).show()
        }
    }



    private fun getData(){
        if(intent.hasExtra(AppConstants.INTENT_EXTRA_APPVO)){
            appVO = intent.getParcelableExtra<AppVO>(AppConstants.INTENT_EXTRA_APPVO)
        }else{
            Toast.makeText(this,"구독데이터 가져오기 실패", Toast.LENGTH_SHORT).show()
            Log.d(TAG,"AppVO Intent 없음")
            finish()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setResultView(){

        register_btn.setBackgroundResource(R.drawable.shape_radius_purple)

        val tmpCalendar = Calendar.getInstance()
        tmpCalendar.time = selectedDate
        if(appVO.app_free==0) {
            register_free.visibility = View.GONE
        }else{
            register_free_period.text = "${DateUtils.getMonthNumber(tmpCalendar.time)}/${DateUtils.getDayNumber(tmpCalendar.time)}"
            tmpCalendar.add(Calendar.MONTH,1)
        }

        if(appVO.app_discount==0) {
            register_discount.visibility = View.GONE
        }else{
            register_discount_period.text = "${DateUtils.getMonthNumber(tmpCalendar.time)}/${DateUtils.getDayNumber(selectedDate!!)}"
            tmpCalendar.add(Calendar.MONTH,1)
            register_discount_charge.text = "3,000원"
        }

        register_normal_period.text = "${DateUtils.getMonthNumber(tmpCalendar.time)}/${DateUtils.getDayNumber(tmpCalendar.time)}~"
        register_normal_charge.text = "${selectedPlan!!.plan_charge}원"
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
        register_app_name.text = appVO.app_label
        // 앱 카테고리
        register_app_category.text = appVO.app_category
    }

    private fun setPlanList(){
        val retrofitClient = RetrofitClient.getInstance()
        val retrofitService = retrofitClient.create(RetrofitService::class.java)
        retrofitService.loadAppPlans(appVO.app_package).enqueue(object :
            Callback<ArrayList<PlanVO>> {
            override fun onFailure(call: Call<ArrayList<PlanVO>>, t: Throwable) {
                Log.d(TAG,"실패 $t")
            }

            override fun onResponse(call: Call<ArrayList<PlanVO>>, response: Response<ArrayList<PlanVO>>) {
                Log.d(TAG,"플랜 리스트 가져오기 성공")

                for(plan in response.body()!!){
                    planList.add(plan.getFullPeriod())
                }
                // 플랜 리스트
                register_plan_list.adapter = PlanAdapter(applicationContext,planList,onPlanItemClick)
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun setCalender(){

        // set current date to calendar and current month to currentMonth variable
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]

        tvYear.text = "${DateUtils.getYear(calendar.time)}년 "
        tvMonth.text = "${DateUtils.getMonthNumber(calendar.time)}월 "

        // enable white status bar with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE

        // calendar view manager is responsible for our displaying logic
        val myCalendarViewManager = object :
            CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                // set date to calendar according to position where we are
                val cal = Calendar.getInstance()
                cal.time = date
                // if item is selected we return this layout items
                // in this example. monday, wednesday and friday will have special item views and other days
                // will be using basic item view
                return if (isSelected)
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        /*Calendar.MONDAY -> R.layout.first_special_selected_calendar_item
                        Calendar.WEDNESDAY -> R.layout.second_special_selected_calendar_item
                        Calendar.FRIDAY -> R.layout.third_special_selected_calendar_item*/
                        else -> R.layout.calendar_selected_item
                    }
                else
                // here we return items which are not selected
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        /*Calendar.MONDAY -> R.layout.first_special_calendar_item
                        Calendar.WEDNESDAY -> R.layout.second_special_calendar_item
                        Calendar.FRIDAY -> R.layout.third_special_calendar_item*/
                        else -> R.layout.calendar_unselected_item
                    }

                // NOTE: if we don't want to do it this way, we can simply change color of background
                // in bindDataToCalendarView method
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                // using this method we can bind data to calendar view
                // good practice is if all views in layout have same IDs in all item views
                holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)

            }
        }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object :
            CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                //tvYear.text = "${DateUtils.getYear(date)}년 "
                //tvMonth.text = "${DateUtils.getMonthNumber(date)}월 "
                selectedDate = date
                if (selectedPlan != null){
                    register_result.visibility = View.VISIBLE
                    setResultView()
                }
                super.whenSelectionChanged(isSelected, position, date)
            }


        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object :
            CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                // in this example sunday and saturday can't be selected, others can
                return when (cal[Calendar.DAY_OF_WEEK]) {
                    /*Calendar.SATURDAY -> false
                    Calendar.SUNDAY -> false*/
                    else -> true
                }
            }
        }

        // here we init our calendar, also you can set more properties if you haven't specified in XML layout
        val singleRowCalendar = main_single_row_calendar.apply {
            this.calendarViewManager = myCalendarViewManager
            this.calendarChangesObserver = myCalendarChangesObserver
            this.calendarSelectionManager = mySelectionManager
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }

        btnRight.setOnClickListener {
            singleRowCalendar.setDates(getDatesOfNextMonth())
        }

        btnLeft.setOnClickListener {
            singleRowCalendar.setDates(getDatesOfPreviousMonth())
        }
    }

    private fun getDatesOfNextMonth(): List<Date> {
        currentMonth++ // + because we want next month
        if (currentMonth == 12) {
            // we will switch to january of next year, when we reach last month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
            currentMonth = 0 // 0 == january
        }
        return getDates(mutableListOf())
    }

    private fun getDatesOfPreviousMonth(): List<Date> {
        currentMonth-- // - because we want previous month
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }


    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }



}
