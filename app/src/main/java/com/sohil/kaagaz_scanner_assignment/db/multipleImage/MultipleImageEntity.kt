package com.sohil.kaagaz_scanner_assignment.db.multipleImage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is a Entity class for multiple album which is used to create the table for and column for a RoomDatabase
 */
@Entity(tableName = "multiple_data_table")
data class MultipleImageEntity(
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}