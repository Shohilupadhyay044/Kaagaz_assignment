package com.sohil.kaagaz_scanner_assignment.db.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity

/**
 * This is Dao class which is use to store the data into room database which name as MyDataBases
 */
@Dao
interface MyDataDao {

    /**
     * insert data of single take image into my_data_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(myDataEntity: MyDataEntity)

     /**
     * get data of single take image from my_data_table
     */
    @Query("select * from my_data_table")
    fun getTask(): LiveData<List<MyDataEntity>>

    /**
     * insert data of multiple take image into multiple_data_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultiple(multipleDataEntity: MultipleImageEntity)

    /**
     * get data of multiple take image from multiple_data_table
     */
    @Query("select * from multiple_data_table")
    fun getTaskMultiple(): LiveData<List<MultipleImageEntity>>
}