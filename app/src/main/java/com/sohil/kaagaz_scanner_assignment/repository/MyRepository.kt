package com.sohil.kaagaz_scanner_assignment.repository


import androidx.lifecycle.LiveData
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataDao
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRepository(private val myDataDao: MyDataDao) {

    /**
     * insert single take photo Data into myEntity table
     */
    fun addImage(imageEntity: MyDataEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            myDataDao.insert(imageEntity)
        }
    }

    /**
     * function pass to viewModel to fetch the single take photo Data from myEntity
     */
    fun getImage(): LiveData<List<MyDataEntity>> {
        return myDataDao.getTask()
    }

    /**
     * insert multiple take photo Data into multipleEntity table
     */
    fun addMultipleImage(multipleImage: MultipleImageEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            myDataDao.insertMultiple(multipleImage)
        }
    }

    /**
     * function pass to viewModel to fetch the multiple take photo Data from multipleEntity
     */
    fun getMultipleImage(): LiveData<List<MultipleImageEntity>> {
        return myDataDao.getTaskMultiple()
    }


}