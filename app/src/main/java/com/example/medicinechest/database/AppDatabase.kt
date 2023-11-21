package com.example.medicinechest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class, User::class], version = 1) //какие таблицы есть внутри бд
abstract class AppDatabase : RoomDatabase() { //наследование рум шаблона
    abstract fun getDao(): MedicineDao
}