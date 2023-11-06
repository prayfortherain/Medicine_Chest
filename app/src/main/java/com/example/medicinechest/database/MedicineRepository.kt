package com.example.medicinechest.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineRepository(private val medicineDao: MedicineDao) {
    suspend fun getNameByID(id: Int) : String{
        return withContext(Dispatchers.IO) {
            return@withContext medicineDao.getNameById(id)
        }
    }
    suspend fun getAround(a: Int, b: Int): List<Medicine>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getAround(a, b)
        }
    }

    suspend fun getById(id: Int) : Medicine {
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getById(id)
        }
    }

    suspend fun getMedicinesList(): List<Medicine>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getMedicinesList()
        }
    }

    suspend fun getMedicineFromList(id: Int): List<Medicine>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getMedicineFromList(id)
        }
    }

    suspend fun getLists(): List<String>{
        return withContext(Dispatchers.IO){
            return@withContext medicineDao.getLists()
        }
    }

}