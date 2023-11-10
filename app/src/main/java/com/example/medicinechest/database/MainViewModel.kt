package com.example.medicinechest.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainVM(private val MedicineRepository: MedicineRepository): ViewModel() {
    var id: MutableLiveData<Int> = MutableLiveData()
    var temp_list1: MutableLiveData<List<String>> = MutableLiveData()
    var medslist: MutableLiveData<List<Medicine>> = MutableLiveData()
    fun getMedicinesList(listName: String){
        viewModelScope.launch {
            medslist.value = MedicineRepository.getMedicinesList(listName)
        }
    }
    fun getLists(){
        viewModelScope.launch {
            temp_list1.value = MedicineRepository.getLists()
        }
    }

    fun insertMedicine(name : String, composition : String, symptoms : String, contraindications : String, storageTemperature : String, sideEffects : String, instruction : String){
        viewModelScope.launch {
            MedicineRepository.insertMedicine(name, composition, symptoms, contraindications, storageTemperature, sideEffects, instruction)
        }
    }

    fun deleteMedicine(medicine: Int){
        viewModelScope.launch {
            if(temp_list1.value!!.size == 1){
                val id = temp_list1.value!!.indexOf(MedicineRepository.getById(medicine).symptoms)
                temp_list1.value!!.drop(id)
            }
            medslist.value!!.drop(medicine)
            MedicineRepository.deleteMedicine(medicine)

        }
    }
}