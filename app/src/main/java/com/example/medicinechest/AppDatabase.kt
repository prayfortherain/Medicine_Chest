package com.example.medicinechest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class, Checklist::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao? // реализовать запросы через этот интерфейс
}