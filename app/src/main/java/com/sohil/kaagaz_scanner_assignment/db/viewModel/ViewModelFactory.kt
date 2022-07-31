package com.sohil.kaagaz_scanner_assignment.db.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sohil.kaagaz_scanner_assignment.repository.MyRepository


class ViewModelFactory(private val listRepository: MyRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(listRepository) as T
    }
}