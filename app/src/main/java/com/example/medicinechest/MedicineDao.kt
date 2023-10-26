package com.example.medicinechest

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Query("SELECT * FROM Medicine") // вот этот запрос фактически выполняется
    suspend fun getAll(): List<Medicine?> // список лекарств получаем

    @Query("SELECT * FROM Medicine WHERE id = :id")
    suspend fun getById(id: Int): Medicine // берем лекарство по ID


}