package com.example.medicinechest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class, Checklist::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): MedicineDao
}