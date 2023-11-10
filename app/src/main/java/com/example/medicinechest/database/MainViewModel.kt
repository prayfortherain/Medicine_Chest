package com.example.medicinechest.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainVM(private val MedicineRepository: MedicineRepository): ViewModel() {
//инициализация
    var temp_list1: MutableLiveData<List<String>> = MutableLiveData()
    var medslist: MutableLiveData<List<Medicine>> = MutableLiveData()

    //получение списка объедененных одним симптомом
    fun getMedicinesList(listName: String){
        viewModelScope.launch { //чтобы не засорять основной код корутинами запускаем здесь
            medslist.value = MedicineRepository.getMedicinesList(listName)
        }
    }
    //получение списка для бокового меню
    fun getLists(){
        viewModelScope.launch {
            temp_list1.value = MedicineRepository.getLists()
        }
    }

    //добавляем без ид
    fun insertMedicine(name : String, composition : String, symptoms : String, contraindications : String, storageTemperature : String, sideEffects : String, instruction : String){
        viewModelScope.launch {
            MedicineRepository.insertMedicine(name, composition, symptoms, contraindications, storageTemperature, sideEffects, instruction)
        }
    }

    //удаляем элемент
    fun deleteMedicine(medicine: Int){
        viewModelScope.launch {
            if(temp_list1.value!!.size == 1){ //решение проблемы с удалением первого элемента
                val id = temp_list1.value!!.indexOf(MedicineRepository.getById(medicine).symptoms)
                temp_list1.value!!.drop(id)
            }
            medslist.value!!.drop(medicine)
            MedicineRepository.deleteMedicine(medicine)

        }
    }
}