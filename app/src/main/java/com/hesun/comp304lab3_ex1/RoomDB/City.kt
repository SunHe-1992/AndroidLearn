package com.hesun.comp304lab3_ex1.RoomDB

@androidx.room.Entity
data class City(
    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Int,

    @androidx.room.ColumnInfo("city")
    var cityName: String

) {

}