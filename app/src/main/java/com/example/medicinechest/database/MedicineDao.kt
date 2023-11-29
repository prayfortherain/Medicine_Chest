package com.example.medicinechest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicineDao {
    @Query("SELECT * FROM Medicine WHERE id = :id")
    suspend fun getById(id: Int): Medicine // берем лекарство по ID

    //@Query("SELECT * FROM Lists")
    //suspend fun getlistsWithMedicines(): List<ListMedicinePair>

    @Update
    fun updateMeds(medicine : Medicine) //обновить

    @Delete
    fun deleteMedicine(medicine: Medicine) //удаляем полученный элемент

    //добавляем новый медикамент без id
    @Query("INSERT INTO Medicine (name, composition, symptoms, contraindications, storageTemperature,sideEffects, instruction) VALUES (:name, :composition, :symptoms, :contraindications, :storageTemperature, :sideEffects, :instruction)")
    fun addMedicine(name : String, composition : String, symptoms : String, contraindications : String, storageTemperature : String, sideEffects : String, instruction : String)

}