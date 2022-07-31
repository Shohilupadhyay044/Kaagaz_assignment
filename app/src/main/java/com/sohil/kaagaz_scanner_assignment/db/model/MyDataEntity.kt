package com.sohil.kaagaz_scanner_assignment.db.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is a Entity class which is used to create the table for and column for a RoomDatabase
 */
@Entity(tableName = "my_data_table")
data class MyDataEntity(
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}