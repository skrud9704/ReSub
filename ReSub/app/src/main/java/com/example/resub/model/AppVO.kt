package com.example.resub.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserApp")
class AppVO {
    constructor()

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

    constructor(app_package : String, app_label : String, app_free : Int, app_discount : Int): this(){
        this.app_package = app_package
        this.app_label = app_label
        this.app_free = app_free
        this.app_discount = app_discount
    }


}