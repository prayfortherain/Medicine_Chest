package com.example.medicinechest.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineRepository(private val medicineDao: MedicineDao) {
    suspend fun getNameByID(name : String) : Int{
        return withContext(Dispatchers.IO) {
            return@withContext medicineDao.getNameById(name)
        }
    }
    suspend fun getById(id: Int) : Medicine {
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getById(id)
        }
    }

    suspend fun getMedicinesList(listName: String): List<Medicine>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getMedicinesList(listName)
        }
    }

    suspend fun getLists(): List<String>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getLists().toSet().toList()
        }
    }

    suspend fun insertMedicine(name : String, composition : String, symptoms : String, contraindications : String, storageTemperature : String, sideEffects : String, instruction : String){
        return withContext(Dispatchers.IO){
            medicineDao.addMedicine(name, composition, symptoms, contraindications, storageTemperature, sideEffects, instruction)
        }
    }

    suspend fun deleteMedicine(medicine: Int){
        return withContext(Dispatchers.IO){
            val medicineEntity = medicineDao.getById(medicine)
            medicineDao.deleteMedicine(medicineEntity)
        }
    }
}