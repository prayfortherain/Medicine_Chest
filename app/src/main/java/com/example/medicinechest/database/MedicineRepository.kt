package com.example.medicinechest.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineRepository(private val medicineDao: MedicineDao) {
    suspend fun getNameByID(id: Int) : String{
        return withContext(Dispatchers.IO) {
            return@withContext medicineDao.getNameById(id)
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
            return@withContext medicineDao.getLists()
        }
    }

    suspend fun insertMedicine(medicine: Medicine){
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.insertMedicine(medicine)
        }
    }

}