package com.example.medicinechest.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineRepository(private val medicineDao: MedicineDao) {
    //получение элемента по ид
    suspend fun getById(id: Int) : Medicine {
        return withContext(Dispatchers.IO){//для чтения или записи файлов, сетевых операций и т.д.
            return@withContext medicineDao.getById(id) // удобные операторы для комбинирования и управления последовательностями асинхронных операций
        }
    }

    //получение списка объедененных одним симптомом
    suspend fun getMedicinesList(listName: String): List<Medicine>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getMedicinesList(listName)
        }
    }

    //получение списка симптомов для бокового списка
    suspend fun getLists(): List<String>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getLists().toSet().toList() //чтобы боковые списки не повторялись
        }
    }

    //добавляем новый медикамент без id
    suspend fun insertMedicine(name : String, composition : String, symptoms : String, contraindications : String, storageTemperature : String, sideEffects : String, instruction : String){
        return withContext(Dispatchers.IO){
            medicineDao.addMedicine(name, composition, symptoms, contraindications, storageTemperature, sideEffects, instruction)
        }
    }

    //удаляем медикамент
    suspend fun deleteMedicine(medicine: Int){
        return withContext(Dispatchers.IO){
            val medicineEntity = medicineDao.getById(medicine) //получаем по id
            medicineDao.deleteMedicine(medicineEntity) //удаляем через запрос
        }
    }
}