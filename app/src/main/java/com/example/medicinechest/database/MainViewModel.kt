package com.example.medicinechest.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicinechest.database.Medicine
import com.example.medicinechest.database.MedicineRepository
import kotlinx.coroutines.launch

class MainVM(private val MedicineRepository: MedicineRepository): ViewModel() {
    var name: MutableLiveData<String> = MutableLiveData<String>()
    var temp_list1: MutableLiveData<List<String>> = MutableLiveData()
    var medslist: MutableLiveData<List<Medicine>> = MutableLiveData()
    var otherlists: MutableLiveData<List<Medicine>> = MutableLiveData()
    fun getNameByID(id: Int){
        viewModelScope.launch {
            name.value = MedicineRepository.getNameByID(id)
        }
    }
    fun getMedicinesList(){
        viewModelScope.launch {
            medslist.value = MedicineRepository.getMedicinesList()
        }
    }
    fun getMedicineFromList(id : Int){
        viewModelScope.launch {
            otherlists.value = MedicineRepository.getMedicineFromList(id)
        }
    }
    fun getLists(){
        viewModelScope.launch {
            temp_list1.value = MedicineRepository.getLists()
        }
    }
}