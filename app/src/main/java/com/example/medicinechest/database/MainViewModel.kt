package com.example.medicinechest.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicinechest.HttpRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class MainVM(private val MedicineRepository: MedicineRepository, private val httpRepository: HttpRepository): ViewModel() {
//инициализация
    var temp_list1: MutableLiveData<List<String>> = MutableLiveData()
    var medslist: MutableLiveData<List<Medicine>> = MutableLiveData()
    var something: MutableLiveData<List<MedicineSerialized>> = MutableLiveData()
    var apcthi : MutableLiveData<MedicineSerialized> = MutableLiveData()
    //получение списка объедененных одним симптомом


    fun getMedicinesList(listName: String){

        viewModelScope.launch { //чтобы не засорять основной код корутинами запускаем здесь
            //medslist.value = MedicineRepository.getMedicinesList(listName)
        }
    }
    //получение списка для бокового меню
    fun getLists(){
        viewModelScope.launch {
            //temp_list1.value = MedicineRepository.getLists()
        }
    }

    fun getSomething(){
        val client = HttpClient()
        viewModelScope.launch {
            val data = client.get<String>("http://37.77.105.18/api/Medicines")
            Log.i("Simple case ", data)
            something.value = Json.decodeFromString<MedicineListWrapper>(data).medicines
        }
    }


    fun getMedicineByName(name: String) {
        val client = HttpClient()
        viewModelScope.launch {
            val data = client.get<String>("http://37.77.105.18/api/Medicines")
            Log.i("Simple case", data)

            val medicineList = Json.decodeFromString<MedicineListWrapper>(data).medicines

            val medicineWithName = medicineList.find { it.name == name }
            if (medicineWithName != null) {
                // Обработайте найденный объект medicineWithName
                apcthi.value = medicineWithName
                Log.i("Found Medicine", medicineWithName.toString())
            } else {
                Log.i("Medicine not found", "No medicine found with the name $name")
            }
        }
    }

    fun postQuery(medicines: String){
        viewModelScope.launch {
            httpRepository.sendMedicineDataToServer(medicines)
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