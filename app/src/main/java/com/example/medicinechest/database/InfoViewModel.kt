package com.example.medicinechest.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InfoViewModel(private val MedicineRepository: MedicineRepository): ViewModel()  {
    var medicine: MutableLiveData<Medicine> = MutableLiveData()
    fun getById(id: Int){
        viewModelScope.launch {
            medicine.value = MedicineRepository.getById(id)
        }
    }
}