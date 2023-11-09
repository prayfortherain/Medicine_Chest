package com.example.medicinechest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.medicinechest.database.Medicine
import org.w3c.dom.ProcessingInstruction

@Dao
interface MedicineDao {
    @Query("SELECT * FROM Medicine where symptoms=:listName")
    suspend fun getMedicinesList(listName: String): List<Medicine>

    @Query("SELECT * FROM Medicine WHERE id = :id")
    suspend fun getById(id: Int): Medicine // берем лекарство по ID

    @Query("SELECT id FROM Medicine WHERE name = :name")
    suspend fun getNameById(name: String): Int

    @Query("SELECT symptoms FROM Medicine") // списки
    suspend fun getLists(): List<String>

    @Update
    fun updateMeds(medicine : Medicine)

    @Delete
    fun deleteMedicine(medicine: Medicine)

    @Query("INSERT INTO Medicine (name, composition, symptoms, contraindications, storageTemperature,sideEffects, instruction) VALUES (:name, :composition, :symptoms, :contraindications, :storageTemperature, :sideEffects, :instruction)")
    fun addMedicine(name : String, composition : String, symptoms : String, contraindications : String, storageTemperature : String, sideEffects : String, instruction : String)
}