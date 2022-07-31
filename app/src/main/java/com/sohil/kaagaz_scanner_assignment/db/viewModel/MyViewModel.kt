package com.sohil.kaagaz_scanner_assignment.db.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity
import com.sohil.kaagaz_scanner_assignment.repository.MyRepository

class MyViewModel(val repository: MyRepository) : ViewModel() {

    /**
     * function call in main activity and call the repository function for single take image.
     */
    fun addImage(customerEntity: MyDataEntity) {
        repository.addImage(customerEntity)
    }

    /**
     * function call in singleTakeActivity and call the repository function to get single take image.
     */
    fun getImage(): LiveData<List<MyDataEntity>> {
        return repository.getImage()
    }

    /**
     * function call in main activity and call the repository function for multiple take image.
     */
    fun addMultipleImage(customerEntity: MultipleImageEntity) {
        repository.addMultipleImage(customerEntity)
    }

    /**
     * function call in singleTakeActivity and call the repository function to get single take image.
     */
    fun getMultipleImage(): LiveData<List<MultipleImageEntity>> {
        return repository.getMultipleImage()
    }
}