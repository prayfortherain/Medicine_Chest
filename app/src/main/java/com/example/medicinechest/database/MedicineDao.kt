package com.example.medicinechest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.medicinechest.database.Checklist
import com.example.medicinechest.database.Medicine

@Dao
interface MedicineDao {
    @Query("SELECT * FROM Medicine")
    suspend fun getMedicinesList(): List<Medicine>

    @Query("SELECT * FROM Medicine WHERE checklist = :id")
    suspend fun getMedicineFromList(id: Int): List<Medicine>

    @Query("SELECT * FROM Medicine WHERE id = :id")
    suspend fun getById(id: Int): Medicine // берем лекарство по ID

    @Query("SELECT * FROM Medicine where id between :a AND :b")
    suspend fun getAround(a: Int, b:Int): List<Medicine>

    @Query("SELECT name FROM Medicine WHERE id = :id")
    suspend fun getNameById(id: Int): String

    //Checklist
    @Query("SELECT name FROM Checklist") // списки
    suspend fun getLists(): List<String>

    @Insert
    fun insertList(name : Checklist)

    @Delete
    fun deleteList(name: Checklist)

    @Update
    fun updateMeds(medicine : Medicine)
}