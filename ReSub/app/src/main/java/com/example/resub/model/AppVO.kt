package com.example.resub.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserApp")
class AppVO() : Parcelable{

    // 패키지명
    @PrimaryKey
    @ColumnInfo(name="app_package")
    @SerializedName("app_package")  // 변경금지
    var app_package : String = ""

    // 앱 이름
    @ColumnInfo(name="app_label")
    @SerializedName("app_label")  // 변경금지
    var app_label : String = ""

    // 무료기한 - 0 : 없음, 1:있음
    @ColumnInfo(name="app_free")
    @SerializedName("app_free")   // 변경금지
    var app_free = -1

    // 할인기한 - 0 : 없음, 1 : 있음
    @ColumnInfo(name="app_discount")
    @SerializedName("app_discount")  // 변경금지
    var app_discount = -1

    // 다중디바이스 지원 - 0 : 없음, 1 : 있음
    @ColumnInfo(name="app_multidevice")
    @SerializedName("app_multidevice")  // 변경금지
    var app_multidevice = -1

    // 앱 색깔
    @ColumnInfo(name="app_color")
    @SerializedName("app_color")  // 변경금지
    var app_color = ""

    // 앱 색깔
    @ColumnInfo(name="app_category")
    @SerializedName("category")  // 변경금지
    var app_category = ""

    constructor(parcel: Parcel) : this() {
        app_package = parcel.readString()!!
        app_label = parcel.readString()!!
        app_free = parcel.readInt()
        app_discount = parcel.readInt()
        app_multidevice = parcel.readInt()
        app_color = parcel.readString()!!
        app_category = parcel.readString()!!
    }

    constructor(app_package : String, app_label : String, app_free : Int, app_discount : Int, app_multidevice : Int, app_color : String, app_category : String): this(){
        this.app_package = app_package
        this.app_label = app_label
        this.app_free = app_free
        this.app_discount = app_discount
        this.app_multidevice = app_multidevice
        this.app_color = app_color
        this.app_category = app_category
    }

    // 생성자와 순서를 맞춰줘야해..
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(app_package)
        dest.writeString(app_label)
        dest.writeInt(app_free)
        dest.writeInt(app_discount)
        dest.writeInt(app_multidevice)
        dest.writeString(app_color)
        dest.writeString(app_category)
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<AppVO> {
        override fun createFromParcel(parcel: Parcel): AppVO {
            return AppVO(parcel)
        }

        override fun newArray(size: Int): Array<AppVO?> {
            return arrayOfNulls(size)
        }
    }


}